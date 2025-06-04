package com.filiera.controller;

import com.filiera.model.products.Prodotto;
import com.filiera.services.CuratoreServiceImpl;

import java.util.List;
import java.util.UUID;

public class CuratoreController {

    private CuratoreServiceImpl curatoreService;

    public CuratoreController(CuratoreServiceImpl curatoreService) {
        this.curatoreService = curatoreService;
    }

    public List<Prodotto> getPendingProducts() {
        try {
            List<Prodotto> pendingProducts = curatoreService.getPendingProducts();

            if(pendingProducts == null||pendingProducts.isEmpty()) {
                throw new IllegalArgumentException("No pending products found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving pending products: " + e.getMessage());
        }
        return null;

    }

    public Prodotto approveProduct(Prodotto prodotto, UUID curatore) {
        try {
            curatoreService.approveProduct(prodotto, curatore);
            return prodotto;
        } catch (Exception e) {
            System.out.println("Error approving product: " + e.getMessage());
            return null;
        }
    }

    public Prodotto rejectProduct(Prodotto prodotto, UUID curatore) {
        try {
            curatoreService.rejectProduct(prodotto, curatore);
            return prodotto;
        } catch (Exception e) {
            System.out.println("Error rejecting product: " + e.getMessage());
            return null;
        }
    }



}
