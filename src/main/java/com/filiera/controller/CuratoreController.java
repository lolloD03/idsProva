package com.filiera.controller;

import com.filiera.model.Curatore;
import com.filiera.model.Products.Prodotto;
import com.filiera.services.CuratoreServiceImpl;

import java.util.List;
import java.util.UUID;

public class CuratoreController {

    private CuratoreServiceImpl curatoreService;

    public CuratoreController(CuratoreServiceImpl curatoreService) {
        this.curatoreService = curatoreService;
    }

    public List<Prodotto> getPendingProducts() {
        return curatoreService.getPendingProducts();
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

}
