package com.filiera.controller;

import com.filiera.model.dto.ProdottoRequestDTO;
import com.filiera.model.products.Prodotto;
import com.filiera.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/venditore")
@Validated
public class VenditoreController {

    private final ProductService service;

    @Autowired
    public VenditoreController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create-product")
    public ResponseEntity<Prodotto> createProduct(
            @RequestBody @Valid ProdottoRequestDTO productDTO,
            @RequestHeader("X-User-Id") UUID sellerId) { // Simulazione header di autenticazione

        Prodotto createdProduct = service.createProduct(productDTO, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Alternativa: endpoint temporaneo per testing
    @PostMapping("/create-product-test")
    public ResponseEntity<Prodotto> createProductForTesting(
            @RequestBody @Valid ProdottoRequestDTO productDTO,
            @RequestParam UUID sellerId) { // Parametro temporaneo per test

        Prodotto createdProduct = service.createProduct(productDTO, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


/*
    @PostMapping("/create-product")
    public ResponseEntity<Prodotto> createProduct(
            @RequestParam @NotNull UUID venditor,
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String descrizione,
            @RequestParam @DecimalMin(value = "0.01", message = "Price must be greater than 0") double price,
            @RequestParam @Min(value = 1, message = "Quantity must be greater than 0") int quantity,
            @RequestParam @NotBlank String certification) {

        Prodotto product = service.createProduct(venditor, name, descrizione, price, quantity, certification);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


 */
    @PutMapping("/update-product")
    public ResponseEntity<Prodotto> updateProduct(
            @PathVariable UUID productId,
            @RequestBody @Valid ProdottoRequestDTO productDTO ,
            @RequestHeader("X-User-Id") UUID sellerId) { // Simulazione header di autenticazione


        Prodotto product = service.updateProduct(productId, productDTO , sellerId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update-product-testing")
    public ResponseEntity<Prodotto> updateProductForTesting(
            @PathVariable UUID productId,
            @RequestBody @Valid ProdottoRequestDTO productDTO ,
            @RequestParam UUID sellerId) { // Simulazione header di autenticazione



        Prodotto product = service.updateProduct(productId, productDTO , sellerId);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/approved-products")
    public ResponseEntity<List<Prodotto>> getApprovedProducts() {
        List<Prodotto> products = service.getApprovedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Prodotto> getById(@PathVariable @NotNull UUID id) {
        Optional<Prodotto> product = service.getById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Prodotto>> getAllProducts() {
        List<Prodotto> products = service.listAll();
        return ResponseEntity.ok(products);
    }
}