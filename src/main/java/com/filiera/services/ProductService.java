package com.filiera.services;

import com.filiera.model.Products.Prodotto;

import java.util.List;
import java.util.UUID;
public interface ProductService {
    Prodotto createProduct(Prodotto product);
    List<Prodotto> listAll();
    Prodotto getById(UUID id);
}
