package com.example.springrsocket.config.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository // for demo purpose, not a real repository, define a stereotype like component
public class UserRepository {
    private final PasswordEncoder passwordEncoder;
    private Map<String, UserDetails> db;


    @PostConstruct
    private void init() {
        this.db = Map.of(
                "user", User.withUsername("user").password(this.passwordEncoder.encode("password")).roles("USER").build(),
                "admin", User.withUsername("admin").password(this.passwordEncoder.encode("password")).roles("ADMIN").build(),
                "client", User.withUsername("client").password(this.passwordEncoder.encode("password")).roles("TRUSTED_CLIENT").build()
        );
    }

    @Autowired
    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails findByUsername(String userName) {
        return this.db.get(userName);
    }
}
