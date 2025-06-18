package com.filiera.controller;

import com.filiera.model.payment.Carrello;
import com.filiera.model.products.Prodotto;
import com.filiera.services.CarrelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {

    private final CarrelloServiceImpl service;

    @Autowired
    public CarrelloController(CarrelloServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Carrello addToCart(@RequestBody Prodotto prodotto) {
        try {
            if (prodotto == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }

            service.addProduct(prodotto);
            return service.getCarrello();
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/remove")
    public Carrello removeFromCart(@RequestBody Prodotto prodotto) {
        try {
            if (prodotto == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            service.removeProduct(prodotto);
            return service.getCarrello();
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/invoice")
    public StringBuilder getInvoice(@RequestBody Carrello carrello){
        try {
            if (carrello == null) {
                throw new IllegalArgumentException("Cart cannot be null");
            }

            if (carrello.getProducts().isEmpty()) {
                throw new IllegalArgumentException("Cart is empty");
            }
            return service.getInvoice(carrello);
        } catch(Exception e) {
            System.out.println("Error getting invoice: " + e.getMessage());
        }

        return null;
    }

    @PostMapping("/clear")
    public void clearCart() {
        try {
            service.clearCarrello();
        } catch (Exception e) {
            System.out.println("Error clearing cart: " + e.getMessage());
        }
    }

    @GetMapping
    public Carrello getCart() {
        return service.getCarrello();
    }

}
