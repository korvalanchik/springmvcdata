package com.example.springmvcdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT username, password, enabled FROM users WHERE username = ?";
        List<UserDetails> users = jdbcTemplate.query(sql, new String[]{username}, (rs, rowNum) ->
                User.withUsername(rs.getString("username"))
                        .password(rs.getString("password"))
                        .disabled(!rs.getBoolean("enabled"))
                        .roles(getAuthorities(username))
                        .build()
        );

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return users.get(0);
    }
    private String[] getAuthorities(String username) {
        String sql = "SELECT authority FROM authorities WHERE username = ?";
        return jdbcTemplate.queryForList(sql, new String[]{username}, String.class).toArray(new String[]{});
    }

    public void addUser(String username, String password, List<String> authorities) {
        // Hash the password using PasswordEncoder
        String encodedPassword = passwordEncoder.encode(password);

        // Insert user details into the users table
        String insertUserSql = "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertUserSql, username, encodedPassword, true);

        // Insert authorities (roles) into the authorities table
        for (String authority : authorities) {
            String insertAuthoritySql = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
            jdbcTemplate.update(insertAuthoritySql, username, authority);
        }
    }

}
