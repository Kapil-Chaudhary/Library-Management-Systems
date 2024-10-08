package com.application.courselibrary.service.impl;

import com.application.courselibrary.entity.User;
import com.application.courselibrary.exception.UserNotFoundException;
import com.application.courselibrary.repository.UserRepository;
import com.application.courselibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
