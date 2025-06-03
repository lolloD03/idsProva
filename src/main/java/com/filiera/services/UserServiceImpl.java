package com.filiera.services;

import com.filiera.model.Curatore;
import com.filiera.model.sellers.Produttore;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;
import com.filiera.repository.CrudRepository;
import com.filiera.repository.InMemoryUserRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final CrudRepository<User , UUID> repo;
    public UserServiceImpl(InMemoryUserRepository repo) { this.repo = repo; }
    @Override
    public User register(User user) {

        if (repo.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("Email già registrata!");
        }
        return repo.save(user);
    }
    @Override public User findById(UUID id) { return repo.findById(id).orElse(null); }

}
