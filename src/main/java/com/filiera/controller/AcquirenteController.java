package com.filiera.controller;

import com.filiera.model.dto.CarrelloResponseDTO;
import com.filiera.model.dto.OrdineResponseDTO;
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
    public ResponseEntity<CarrelloResponseDTO> addToCart(
            @RequestParam @NotNull(message = "Product Id cannot be null") UUID prodotto ,
            @RequestParam @Min( value = 1 , message = "Quantity must be at least 1") int quantity ,
            @RequestParam @NotNull (message = "Buyer Id cannot be null") UUID buyerId) {

            log.info("Adding product {} with quantity {} to cart for buyer {}", prodotto, quantity, buyerId);

        // Lascia che le eccezioni si propaghino al GlobalExceptionHandler
            CarrelloResponseDTO carrello = service.addProduct(prodotto , quantity , buyerId);

            log.info("Product successfully added to cart for buyer {}", buyerId);
            return ResponseEntity.ok(carrello);
    }

    @PostMapping("/remove")
    public ResponseEntity<CarrelloResponseDTO> removeFromCart(@RequestParam @NotNull(message = "Product ID cannot be null") UUID prodotto,
                                                   @RequestParam @Min(value = 1, message = "Quantity must be at least 1") int quantity,
                                                   @RequestParam @NotNull(message = "Buyer ID cannot be null") UUID buyerId) {

            log.info("Removing product {} with quantity {} from cart for buyer {}", prodotto, quantity, buyerId);


        CarrelloResponseDTO carrello = service.removeProduct(prodotto , quantity , buyerId); // Il service restituisce già il DTO

            log.info("Product successfully removed from cart for buyer {}", buyerId);
            return ResponseEntity.ok(carrello);
    }

    /*
    @PostMapping("/invoice")
    public ResponseEntity<StringBuilder> getInvoice(@RequestParam @NotNull (message = "Buyer ID cannot be null") UUID buyerId) {

        log.info("Generating invoice for buyer {}", buyerId);

        Carrello carrello = service.getCarrello(buyerId);

        StringBuilder invoice = service.getInvoice(buyerId);

        log.info("Invoice successfully generated for buyer {}", buyerId);
        return ResponseEntity.ok(invoice);

    }*/

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {
        log.info("Clearing cart for buyer {}", buyerId);

        service.clearCarrello(buyerId);

        log.info("Cart successfully cleared for buyer {}", buyerId);
        return ResponseEntity.noContent().build(); // 204 No Content è più appropriato
    }

    @GetMapping
    public ResponseEntity<CarrelloResponseDTO> getCart(@RequestParam  @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {
        log.debug("Retrieving cart for buyer {}", buyerId);

        CarrelloResponseDTO carrelloResponse = service.getCarrello(buyerId); // Il service restituisce già il DTO

        return ResponseEntity.ok(carrelloResponse);
    }


    @PostMapping("/buy")
    public ResponseEntity<OrdineResponseDTO> buyCart(@RequestParam @NotNull (message = "Buyer ID cannot be null")UUID buyerId) {

        log.debug("Buying cart for buyer {}", buyerId);

        OrdineResponseDTO ordine = service.buyCart(buyerId);
        return ResponseEntity.ok(ordine);

    }



}
