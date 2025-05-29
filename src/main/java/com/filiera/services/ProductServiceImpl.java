package com.filiera.services;

import com.filiera.model.Prodotto;
import com.filiera.repository.InMemoryProductRepository;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private final InMemoryProductRepository repo;
    public ProductServiceImpl(InMemoryProductRepository repo) { this.repo = repo; }
    @Override public Prodotto createProduct(Prodotto product) { return repo.save(product); }
    @Override public List<Prodotto> listAll() { return repo.findAll(); }
    @Override public Prodotto getById(UUID id) { return repo.findById(id).orElse(null); }
}
