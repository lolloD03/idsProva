package com.filiera.controller;

import com.filiera.model.Products.Prodotto;
import com.filiera.services.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    public List<Prodotto> list() { return service.listAll(); }
}
