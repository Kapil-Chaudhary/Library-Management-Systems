package com.application.courselibrary.repository;

import com.application.courselibrary.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    Optional<Transaction> findById(Long integer);

    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByBookId(Long bookId);
//    void deleteById(Long id);

    void deleteById(Integer integer);

    // Query to find all open transactions (without status is true)
    @Query("SELECT t FROM Transaction t WHERE t.status IS true ")
    List<Transaction> findOpenTransactions();


    // other methods if we want


}
