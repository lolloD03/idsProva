package com.filiera.services;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;

public class VenditoreServiceImpl {

    public InMemoryProductRepository productRepository;
    public InMemoryUserRepository userRepository;
    public ProductServiceImpl productService;

    public VenditoreServiceImpl(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }





}
