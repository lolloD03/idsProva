package com.filiera.services;

import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User register(User user);
    Optional<User> findById(UUID id);

}