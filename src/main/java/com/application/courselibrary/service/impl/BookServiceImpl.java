package com.application.courselibrary.service.impl;

import com.application.courselibrary.entity.Book;
import com.application.courselibrary.repository.BookRepository;
import com.application.courselibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book findBookById(Long id){
        Book book=bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        return book;
    }

    public void createBook(Book book){
        bookRepository.save(book);
    }

    public void updateBook(Book book){
        bookRepository.save(book);
    }

    public void deleteBook(Long id){
        Book book=bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        bookRepository.deleteById(book.getId());
    }

//    @Override
//    public Li
//    st<Book> searchBook(String title) {
//        return this.bookRepository.findByTitleContainingIgnoreCase(title);
//    }

    @Override
    public List<Book> getAvailableBooks() {
        return this.bookRepository.findByAvailability(true);
    }

    @Override
    public List<Book> getAllIssuedBooks() {
        return this.bookRepository.findAllIssuedBooks();
    }

    @Override
    public Book saveOrUpdateBook(Book book) {
        return this.bookRepository.save(book);
    }
}
