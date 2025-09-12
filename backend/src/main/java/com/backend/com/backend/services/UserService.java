package com.backend.com.backend.services;

import com.backend.com.backend.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);
}
