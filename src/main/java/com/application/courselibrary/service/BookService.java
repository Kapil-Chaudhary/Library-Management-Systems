package com.application.courselibrary.service;

import com.application.courselibrary.entity.Book;

import com.application.courselibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

    // Get all books
    public List<Book> findAllBooks();

    // Get book by ID
    public Book findBookById(Long id);

    public void createBook(Book book);

    public void updateBook(Book book);

    // Delete a book by id
    public void deleteBook(Long id);


    // Search for books by title - I have removed this title
//    public List<Book> searchBook(String title);

    // Get all available books
    public List<Book> getAvailableBooks();

    // Get all the books that are not available -- issued books
    public List<Book> getAllIssuedBooks();

    // Save or update book
    // public void addBook(Book book);
    // public void updateBook(Book book);
    public Book saveOrUpdateBook(Book book); // here I marge add and update

}
