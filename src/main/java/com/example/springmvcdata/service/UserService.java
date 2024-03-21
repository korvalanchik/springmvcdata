package com.example.springmvcdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUser(String username, String password, String... roles) {
        String insertUserSql = "INSERT INTO users (username, password, enabled) VALUES (?, ?, true)";
        jdbcTemplate.update(insertUserSql, username, password);

        String insertAuthoritySql = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
        for (String role : roles) {
            jdbcTemplate.update(insertAuthoritySql, username, role);
        }
    }

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

}
