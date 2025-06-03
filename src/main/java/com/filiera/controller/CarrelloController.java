package com.filiera.controller;

import com.filiera.model.Products.Carrello;
import com.filiera.model.Products.Prodotto;
import com.filiera.services.AcquirenteServiceImpl;

public class CarrelloController {

    private final AcquirenteServiceImpl service;

    public CarrelloController(AcquirenteServiceImpl service) {
        this.service = service;
    }


    public Prodotto addToCart(Prodotto prodotto) {
        try {
            if (prodotto == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            service.addProduct(prodotto);
            return prodotto;
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
            return null;
        }
    }

    public Prodotto removeFromCart(Prodotto prodotto) {
        try {
            if (prodotto == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            service.removeProduct(prodotto);
            return prodotto;
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
            return null;
        }
    }

    public Carrello getCart() {
        return service.getCarrello();
    }

}
