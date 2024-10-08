package com.application.courselibrary.controller;


import com.application.courselibrary.entity.User;
import com.application.courselibrary.repository.UserRepository;
import com.application.courselibrary.service.UserService;
import com.application.courselibrary.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list"; // Return the Thymeleaf template name
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/detail"; // Return the Thymeleaf template name
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form"; // Return the Thymeleaf template name
    }

    @PostMapping
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors() ){
            model.addAttribute("user", user);
            return "user/form";
        }


        // Check if email is already taken
        if (userService.isEmailTaken(user.getEmail())) {
            result.rejectValue("email", "error.email", "Email already exists!");
            model.addAttribute("user", user);
            return "user/form";
        }

        userService.saveUser(user);
        return "redirect:/users";
    }


    // Show form for editing an existing user
    @GetMapping("{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = this.userService.getUserById(id);
        System.out.println(user);
        if (user != null) {
            model.addAttribute("user", user);
            return "user/form";  // Reuse the user-form.html for editing the user
        }
        return "redirect:/users";  // If the user doesn't exist, redirect to the list
    }


    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        this.userService.deleteUserById(id);
        return "redirect:/users";
    }

}

