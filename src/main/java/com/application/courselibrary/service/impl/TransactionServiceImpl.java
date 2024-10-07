package com.application.courselibrary.service.impl;

import com.application.courselibrary.entity.Book;
import com.application.courselibrary.entity.Transaction;
import com.application.courselibrary.entity.User;
import com.application.courselibrary.repository.BookRepository;
import com.application.courselibrary.repository.TransactionRepository;
import com.application.courselibrary.repository.UserRepository;
import com.application.courselibrary.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    // Get all transactions
    @Override
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }

    // Get transaction by ID
    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // Issue a book (create transaction)
    @Override
    public Transaction issueTransaction(Long userId, Long bookId, LocalDate issueDate, LocalDate returnDate) {

        Optional<Book> book = this.bookRepository.findById(bookId);
        Optional<User> user = this.userRepository.findById(userId);

        if ( book.isPresent() && book.get().getAvailability() && user.isPresent() ) {
            book.get().setAvailability(false);

//            this.bookRepository.save(book.get()).setAvailability(true);

            Transaction transaction = new Transaction();
            transaction.setBook(book.get());
            transaction.setUser(user.get());
            transaction.setIssueDate(issueDate);
            transaction.setReturnDate(returnDate);
            transaction.setStatus(true);
            transactionRepository.save(transaction);

            user.get().getBooks().add(book.get());
            this.userRepository.save(user.get()); // save and update in the book

            return transaction;
        }
        else throw new RuntimeException("Book is not available or User not found.");
    }

    // Return a book (close transaction), status is false
    @Override
    public Transaction returnBook(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction t = transaction.get();
            t.setReturnDate(LocalDate.now());
            t.getBook().setAvailability(true); // Mark book as available
            t.setStatus(false); // Mark this transaction as close
            transaction.get().getUser().getBooks().remove(t.getBook()); // remove this book from user list

            return transactionRepository.save(t);
        }
        else throw new RuntimeException("Transaction not found.");

    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return this.transactionRepository.findByUserId(userId);
    }

    @Override
    public List<Transaction> getOpenTransactions() {
        return this.transactionRepository.findOpenTransactions();
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        this.transactionRepository.deleteById(transactionId);
    }

    @Override
    public void deleteAllCloseTransactions() {
        List<Transaction> all = this.transactionRepository.findAll();
        for (Transaction t : all) {
            if ( t.isStatus()==false ) this.transactionRepository.deleteById(Integer.parseInt(t.getId()+""));
        }
    }


}
