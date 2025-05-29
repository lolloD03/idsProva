package com.filiera.services;

import com.filiera.model.users.User;
import com.filiera.repository.InMemoryUserRepository;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final InMemoryUserRepository repo;
    public UserServiceImpl(InMemoryUserRepository repo) { this.repo = repo; }
    @Override public User register(User user) { return repo.save(user); }
    @Override public User findById(UUID id) { return repo.findById(id).orElse(null); }
    @Override public List<User> listAll() { return repo.findAll(); }
}
