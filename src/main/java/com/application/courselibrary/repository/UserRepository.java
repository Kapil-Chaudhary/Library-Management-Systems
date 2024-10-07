package com.application.courselibrary.repository;

import com.application.courselibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    public User save(User user);

    public Optional<User> findById(Long id);

    public void deleteById(Long id);

    public void delete(User user);

}