package com.filiera.controller;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.services.ProductService;
import com.filiera.services.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }


    public Prodotto createProduct(Venditore venditor, String name, String descrizione, double price, int quantity) {

        try {
            if (venditor == null || name == null || descrizione == null || price <= 0 || quantity <= 0) {
                throw new IllegalArgumentException("Invalid product details provided.");
            }

            return service.createProduct(venditor, name, descrizione, price, quantity);

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating product: " + e.getMessage());
            return null;
        }

    }

    public Prodotto updateProduct(Prodotto updatedProduct) {

        try {
            if (!service.existsById(updatedProduct.getId())) {
                throw new IllegalArgumentException("Doesn't exist a product with this id");
            }

            return service.updateProduct(updatedProduct);

        } catch (IllegalArgumentException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return null;
        }

    }

    public void deleteProduct(Prodotto toDeleteProduct) {

        try {
            if (!service.existsById(toDeleteProduct.getId())) {
                throw new IllegalArgumentException("Doesn't exist a product with this id");
            }

            service.deleteProduct(toDeleteProduct);
        } catch (IllegalArgumentException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }


    public List<Prodotto> getApprovedProducts() {
        return service.getApprovedProducts();
    }

    public Prodotto getById(UUID id) { return service.getById(id); }

    public List<Prodotto> list() { return service.listAll(); }
}
