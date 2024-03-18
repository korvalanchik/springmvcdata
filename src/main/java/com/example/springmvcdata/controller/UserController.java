package com.example.springmvcdata.controller;

import com.example.springmvcdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/add-user")
    public String showAddUserForm() {
        return "add-user";
    }

    @PostMapping("/add-user")
    public ModelAndView addUser(@RequestParam String username, @RequestParam String password, @RequestParam String[] roles) {
        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(password);
        // Add the new user
        userService.addUser(username, encodedPassword, roles);
        // Redirect to a success page
        return new ModelAndView("redirect:/");
    }
}