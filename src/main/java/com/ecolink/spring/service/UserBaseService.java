package com.ecolink.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.repository.UserBaseRepository;

@Service
public class UserBaseService {
    @Autowired
    private UserBaseRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserBase newUser(UserBase user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Optional<UserBase> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<UserBase> findById(Long id) {
        return repository.findById(id);
    }
}
