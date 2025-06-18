package com.filiera.controller;

import com.filiera.model.products.Prodotto;
import com.filiera.services.CuratoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/curatore")
public class CuratoreController {

    private final CuratoreServiceImpl curatoreService;

    @Autowired
    public CuratoreController(CuratoreServiceImpl curatoreService) {
        this.curatoreService = curatoreService;
    }

    @GetMapping("/pending-products")
    public List<Prodotto> getPendingProducts() {
        try {
            List<Prodotto> pendingProducts = curatoreService.getPendingProducts();

            if(pendingProducts == null||pendingProducts.isEmpty()) {
                System.out.println("No pending products found.");
                return Collections.emptyList();
            }
            return pendingProducts;

        } catch (Exception e) {
            System.out.println("Error retrieving pending products: " + e.getMessage());
            return null;

        }

    }

    @PostMapping("/approve-product")
    public Prodotto approveProduct(@RequestBody Prodotto prodotto, @RequestParam UUID curatore) {
        try {
            return curatoreService.approveProduct(prodotto, curatore);
        } catch (Exception e) {
            System.out.println("Error approving product: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/reject-product")
    public Prodotto rejectProduct(@RequestBody Prodotto prodotto, @RequestParam UUID curatore) {
        try {
            return curatoreService.rejectProduct(prodotto, curatore);
        } catch (Exception e) {
            System.out.println("Error rejecting product: " + e.getMessage());
            return null;
        }
    }



}

