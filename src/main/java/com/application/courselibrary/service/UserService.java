package com.application.courselibrary.service;


import com.application.courselibrary.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User saveUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public Optional<User> getUserByEmail(String email);

    public void deleteUserById(Long id);


    // email for validation
    boolean isEmailTaken(String email);

}
