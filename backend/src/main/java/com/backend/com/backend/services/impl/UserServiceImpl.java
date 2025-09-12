package com.backend.com.backend.services.impl;

import com.backend.com.backend.entities.User;
import com.backend.com.backend.repositories.UserRepository;
import com.backend.com.backend.services.UserService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
