package com.application.courselibrary.controller;

import com.application.courselibrary.entity.Book;
import com.application.courselibrary.service.AuthorService;
import com.application.courselibrary.service.BookService;
import com.application.courselibrary.service.CategoryService;
import com.application.courselibrary.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private AuthorService authorService;

    // list books
    @GetMapping("/books")
    public String findAllBooks(Model model){

        List<Book> books=bookService.findAllBooks();
        model.addAttribute("books",books);
        return "books";
    }

    // get book by id -- view a book
    @GetMapping("/book/{id}")
    public String findBook(@PathVariable Long id, Model model){
        System.out.println("view a book"); // eye icon
        Book book=bookService.findBookById(id);
        model.addAttribute("book",book);
        return "list-book";
    }

    // delete book by id
    @GetMapping("remove-book/{id}")
    public String deleteBook(@PathVariable Long id,Model model){
        System.out.println("delete book by id " + id);
        bookService.deleteBook(id);
        model.addAttribute("books",bookService.findAllBooks());
        return "books";
    }


    // edit
    @GetMapping("/update-book/{id}")
    public String updateBook(@PathVariable Long id, Model model) {
        Book book = bookService.findBookById(id);
        System.out.println("edit book" + book);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("publishers", publisherService.findAllPublishers());
        model.addAttribute("authors", authorService.findAllAuthors());
        return "update-book";
    }

    // save after edit
    @PostMapping("/save-update/{id}")
    public String updateBook(@PathVariable Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-book";
        }
        bookService.updateBook(book);
        model.addAttribute("books", bookService.findAllBooks());
        System.out.println("save after update");
        return "redirect:/books";
    }

    // add book
    @GetMapping("/add-book")
    public String addBook(Book book, Model model) {
        System.out.println("add book");
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("publishers", publisherService.findAllPublishers());
        model.addAttribute("authors", authorService.findAllAuthors());
        return "add-book";
    }

    // save after add
    @PostMapping("/save-book")
    public String savebook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        this.bookService.createBook(book);
        model.addAttribute("books", bookService.findAllBooks());
        System.out.println("save after create"); // and redirect to list-book
        return "redirect:/books";
    }


    // currently issued book -- not available
    @GetMapping("/issued-books")
    public String listIssuedBooks(Model model) {
        model.addAttribute("issuedBooks", this.bookService.getAllIssuedBooks());
        return "issued-book-list"; // Thymeleaf template to display the list of issued books
    }

}
