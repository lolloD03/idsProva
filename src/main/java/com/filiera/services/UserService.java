package com.filiera.services;

import com.filiera.model.sellers.Produttore;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User register(User user);
    User findById(UUID id);
    void deleteById(UUID id);

    Iterable<User> findAll();

    User registerProduttore(Produttore produttore);
}