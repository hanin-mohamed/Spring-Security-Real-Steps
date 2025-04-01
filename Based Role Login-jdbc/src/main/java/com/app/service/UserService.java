package com.app.service;


import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public void register(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        String sql = "insert into users (username, password, enabled, first_name, last_name, email) values (?,?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), encryptedPassword, user.isEnabled(), user.getFirstName(), user.getLastName(), user.getEmail());

        String authoritySql = "insert into  authorities (username, authority) values (?, ?)";
        jdbcTemplate.update(authoritySql, user.getUsername(), "ROLE_USER");
    }
}
