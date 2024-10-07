package com.application.courselibrary.service;



import com.application.courselibrary.entity.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    public List<Transaction> getAllTransactions();

    public Optional<Transaction> getTransactionById(Long id);

    // use Issue a book ( create trans... )
    public Transaction issueTransaction(Long userId, Long bookId, LocalDate issueDate, LocalDate returnDate);
//    public Transaction issueBook(Transaction transaction);

    // return a book (close transaction)
    public Transaction returnBook(Long transactionId);

    public List<Transaction> getTransactionsByUserId(Long userId);

    public List<Transaction> getOpenTransactions();

    // delete - close transaction by id
    public void deleteTransaction(Integer transactionId);

    // delete all close transaction
    public void deleteAllCloseTransactions();

}
