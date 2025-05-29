package com.filiera.services;

import com.filiera.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(User user);
    User findById(UUID id);
    List<User> listAll();
}