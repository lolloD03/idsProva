package com.filiera.services;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;

import java.util.List;
import java.util.UUID;
public interface ProductService {
    Prodotto createProduct(Venditore venditore, String name, String descrizione, double price, int quantity);
    List<Prodotto> listAll();
    Prodotto getById(UUID id);
    Prodotto updateProduct(Prodotto updatedProduct);
    void deleteProduct(Prodotto deletedProduct);

    public List<Prodotto> getApprovedProducts();

    boolean existsInRepoById(UUID id);
}
