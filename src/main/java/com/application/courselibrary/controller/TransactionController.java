package com.application.courselibrary.controller;


import com.application.courselibrary.entity.Transaction;
import com.application.courselibrary.exception.ResourceNotFoundException;
import com.application.courselibrary.service.BookService;
import com.application.courselibrary.service.TransactionService;
import com.application.courselibrary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    // Display list of transactions
    @GetMapping
    public String listTransactions(Model model) {
        List<Transaction> transactions = this.transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        transactions.forEach(transaction -> System.out.println(transaction));
        return "transaction-list";  // This will return the transaction-list.html template
    }

    // Show form for issuing a new book (creating a new transaction)
    @GetMapping("/new")
    public String showIssueBookForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("books", bookService.getAvailableBooks());
        model.addAttribute("users", userService.getAllUsers());
        return "transaction-form";  // This will return the transaction-form.html for issuing a book
    }

    // Handle form submission for saving a new transaction (issuing a book)
    @PostMapping("/save")
    public String saveTransaction(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult result, Model model) {

        if ( transaction.getUser()==null || transaction.getUser().getId()==null ) result.rejectValue("user", "error.user", "User must be selected.");
        if ( transaction.getBook()==null || transaction.getBook().getId()==null ) result.rejectValue("book", "error.book", "Book must be selected.");


        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("books", bookService.getAvailableBooks());
            return "transaction-form";
        }

        Long bookId = transaction.getBook().getId();
        Long userId = transaction.getUser().getId();
        LocalDate issueDate = transaction.getIssueDate();
        LocalDate returnDate = transaction.getReturnDate();

        transactionService.issueTransaction(userId, bookId, issueDate, returnDate);

        System.out.println(transaction);

        return "redirect:/transactions";  // After saving, redirect back to the transactions list
    }

    // Handle request to return a book
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id).orElseThrow( () -> new ResourceNotFoundException("Transaction Not Found with id + " + id));
        if (transaction != null && transaction.getReturnDate()!=null )  transactionService.returnBook(transaction.getId());
        return "redirect:/transactions";  // Redirect back to the transaction list after returning
    }

    // to delete all the close transaction
    @PostMapping("/delete-closed")
    public String deleteClosedTransactions() {
        transactionService.deleteAllCloseTransactions();
        return "redirect:/transactions"; // Redirect to the transaction list after deletion
    }

    // delete transaction by id
    @GetMapping("/delete/{id}")
    public String deleteClosedTransactionsById(@PathVariable Integer id) {
        System.out.println("deleteClosedTransactions by id: " + id );
        this.transactionService.deleteTransaction(id);
        return "redirect:/transactions"; // Redirect to the transaction list after deletion
    }


}