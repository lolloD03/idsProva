package com.filiera.controller;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }
    public Prodotto createProduct(Venditore venditor, String name, String descrizione, double price, int quantity) {

        return service.createProduct(venditor, name, descrizione, price, quantity);
    }

    public List<Prodotto> list() { return service.listAll(); }
}
