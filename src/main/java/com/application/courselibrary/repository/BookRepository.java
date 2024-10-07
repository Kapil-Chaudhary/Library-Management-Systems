package com.application.courselibrary.repository;

import com.application.courselibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book,Long> {

    public Book save(Book book);

    public Optional<Book> findById(int id);

    public List<Book> findAll();

    public void delete(Book book);


    // 2. Derived Queries (Method Naming Conventions)
    List<Book> findByAvailability(boolean available); // --


    @Query("select b from Book b where b.availability")
    List<Book> findAllIssuedBooks();

}


/*


// 3. Query methods - JPQL / Native-SQL query
   @Query("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :title, '%')")
   List<Book> findByTitleContaining(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);


 */