package com.filiera.controller;

import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/venditore")
public class VenditoreController {
    private final ProductService service;

    @Autowired
    public VenditoreController(ProductService service) { this.service = service; }

    @PostMapping("/create-product")
    public Prodotto createProduct(@RequestBody Venditore venditor, @RequestParam String name, @RequestParam String descrizione, @RequestParam double price, @RequestParam int quantity, @RequestParam String certification) {

        try {
            if (venditor == null || name == null || descrizione == null || price <= 0 || quantity <= 0) {
                throw new IllegalArgumentException("Invalid product details provided.");
            }

            return service.createProduct(venditor, name, descrizione, price, quantity,certification);

        } catch (IllegalArgumentException e) {
            System.err.println("Error creating product: " + e.getMessage());
            return null;
        }

    }

    @PutMapping("/update-product")
    public Prodotto updateProduct(@RequestBody Prodotto updatedProduct) {

        try {
            return service.updateProduct(updatedProduct);
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            return null;
        }

    }

    @DeleteMapping("/delete-product")
    public void deleteProduct(@RequestBody Prodotto toDeleteProduct) {

        try {
            service.deleteProduct(toDeleteProduct);
        } catch (IllegalArgumentException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    @GetMapping("/approved-products")
    public List<Prodotto> getApprovedProducts() {
        return service.getApprovedProducts();
    }

    @GetMapping("/product/{id}")
    public Optional<Prodotto> getById(@PathVariable UUID id) { return service.getById(id); }

    @GetMapping("/list")
    public List<Prodotto> list() { return service.listAll(); }
}
