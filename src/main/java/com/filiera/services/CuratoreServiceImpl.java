package com.filiera.services;

import com.filiera.model.users.User;
import com.filiera.repository.InMemoryProductRepository;

import java.util.List;
import java.util.UUID;

public class CuratoreServiceImpl implements UserService {

    private final InMemoryProductRepository productRepository;

    public CuratoreServiceImpl(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public User register(User user) {
        // Implement registration logic for Curatore
        return null; // Placeholder return
    }

    @Override
    public User findById(UUID id) {
        // Implement logic to find Curatore by ID
        return null; // Placeholder return
    }


}
