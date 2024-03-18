package com.example.springmvcdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

 //  user has role USER and password is codejava. And the second user admin has role ADMIN with password is nimda.
@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUser(String username, String password, String... roles) {
        // Insert the user into the users table
        String insertUserSql = "INSERT INTO users (username, password, enabled) VALUES (?, ?, true)";
        jdbcTemplate.update(insertUserSql, username, password);

        // Insert the user's roles into the authorities table
        String insertAuthoritySql = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
        for (String role : roles) {
            jdbcTemplate.update(insertAuthoritySql, username, role);
        }
    }
}