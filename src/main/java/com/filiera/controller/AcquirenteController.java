package com.filiera.controller;

import com.filiera.model.payment.Carrello;
import com.filiera.model.payment.Ordine;
import com.filiera.services.CarrelloServiceImpl;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carrello")
@Slf4j

public class AcquirenteController {

    private final CarrelloServiceImpl service;

    @Autowired
    public AcquirenteController(CarrelloServiceImpl service  ) {this.service = service;}

    @PostMapping("/add")
    public ResponseEntity<Carrello> addToCart(
            @RequestParam @NotNull(message = "Product Id cannot be null") UUID product ,
            @RequestParam @Min( value = 1 , message = "Quantity must be at least 1") int quantity ,
            @RequestParam @NotNull (message = "Buyer Id cannot be null") UUID buyerId) {

            log.info("Adding product {} with quantity {} to cart for buyer {}", product, quantity, buyerId);


            service.addProduct(product , quantity , buyerId);

        // Lascia che le eccezioni si propaghino al GlobalExceptionHandler
            Carrello cart = service.getCarrello(buyerId);

            log.info("Product successfully added to cart for buyer {}", buyerId);
            return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Carrello> removeFromCart(@RequestParam @NotNull(message = "Product ID cannot be null") UUID product,
                                                   @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity,
                                                   @RequestParam @NotNull(message = "Buyer ID cannot be null") UUID buyerId) {

            log.info("Removing product {} with quantity {} from cart for buyer {}", product, quantity, buyerId);

            service.removeProduct(product , quantity , buyerId);

            Carrello cart = service.getCarrello(buyerId);

            log.info("Product successfully removed from cart for buyer {}", buyerId);
            return ResponseEntity.ok(cart);
    }

    @PostMapping("/invoice")
    public ResponseEntity<StringBuilder> getInvoice(@RequestParam @NotNull (message = "Buyer ID cannot be null") UUID buyerId) {

        log.info("Generating invoice for buyer {}", buyerId);

        Carrello cart = service.getCarrello(buyerId);

        StringBuilder invoice = service.getInvoice(buyerId);

        log.info("Invoice successfully generated for buyer {}", buyerId);
        return ResponseEntity.ok(invoice);

    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {
        log.info("Clearing cart for buyer {}", buyerId);

        service.clearCarrello(buyerId);

        log.info("Cart successfully cleared for buyer {}", buyerId);
        return ResponseEntity.noContent().build(); // 204 No Content è più appropriato
    }

    @GetMapping
    public ResponseEntity<Carrello> getCart(@RequestParam  @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {
        log.debug("Retrieving cart for buyer {}", buyerId);

        Carrello cart = service.getCarrello(buyerId);

        // Se il carrello è vuoto, restituisci comunque 200 con carrello vuoto
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/buy")
    public ResponseEntity<Ordine> buyCart(@RequestParam @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {

        log.debug("Buying cart for buyer {}", buyerId);

        Ordine order = service.buyCart(buyerId);
        return ResponseEntity.ok(order);

    }



}
