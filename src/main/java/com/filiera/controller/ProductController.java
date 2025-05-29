package com.filiera.controller;

import com.filiera.model.Prodotto;
import com.filiera.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    public List<Prodotto> list() { return service.listAll(); }
}
