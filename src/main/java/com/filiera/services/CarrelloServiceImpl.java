package com.filiera.services;

import com.filiera.exception.ProductNotFoundException;
import com.filiera.model.dto.CarrelloResponseDTO;
import com.filiera.model.dto.ItemCarrelloResponseDTO;
import com.filiera.model.payment.Carrello;
import com.filiera.model.payment.ItemCarrello;
import com.filiera.model.payment.ItemOrdine;
import com.filiera.model.products.Prodotto;
import com.filiera.model.products.StatoProdotto;
import com.filiera.model.users.Acquirente;
import com.filiera.repository.InMemoryAcquirenteRepository;
import com.filiera.repository.InMemoryCarrelloRepository;
import com.filiera.repository.InMemoryOrdineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.filiera.model.payment.Ordine;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class CarrelloServiceImpl {

    private final ProductService productService;

    private final InMemoryCarrelloRepository cartRepo;

    private final InMemoryAcquirenteRepository buyerRepo;

    private final InMemoryOrdineRepository ordineRepo;

    public CarrelloServiceImpl(ProductService productService , InMemoryCarrelloRepository cartRepo, InMemoryAcquirenteRepository buyerRepo, InMemoryOrdineRepository ordineRepo) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.buyerRepo = buyerRepo;
        this.ordineRepo = ordineRepo;
    }


    // Metodo helper per mappare l'entità Carrello al DTO di risposta
    private CarrelloResponseDTO mapToCarrelloResponseDTO(Carrello carrello) {
        if (carrello == null) {
            return null;
        }

        List<ItemCarrelloResponseDTO> itemDTOs = carrello.getProducts().stream()
                .map(item -> ItemCarrelloResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .unitPrice(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .subtotal(item.getProduct().getPrice() * item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CarrelloResponseDTO.builder()
                .id(carrello.getId())
                .buyerId(carrello.getBuyer().getId())
                .items(itemDTOs)
                .totalPrice(carrello.getTotalPrice()) // Assumendo che getTotalPrice() calcoli dinamicamente
                .build();
    }

    public CarrelloResponseDTO addProduct(UUID prod , int quantity , UUID buyerId) {


        Prodotto prodotto = productService.getById(prod)
                .orElseThrow(() -> new ProductNotFoundException("Il prodotto con id " + prod + " non esiste."));

        Carrello carrello = getCarrelloEntity(buyerId);

        carrello.addProduct(prodotto , quantity);

        cartRepo.save(carrello);
        return mapToCarrelloResponseDTO(carrello);
    }

    public CarrelloResponseDTO removeProduct(UUID prod , int quantity , UUID buyerId) {
        Prodotto prodotto = productService.getById(prod)
                .orElseThrow(() -> new ProductNotFoundException("Il prodotto con id " + prod + " non esiste."));
// ...

        Carrello carrello = getCarrelloEntity(buyerId);

        if(cartIsEmpty(buyerId)) {
            throw new RuntimeException("Il carrello è vuoto");
        }

        carrello.removeProduct(prodotto, quantity);

        cartRepo.save(carrello);
        return mapToCarrelloResponseDTO(carrello);
    }


   public void clearCarrello(UUID buyerId) {

        Carrello carrello = getCarrelloEntity(buyerId);

        if(cartIsEmpty(buyerId)) {
           throw new RuntimeException("Il carrello è vuoto");
       }

       carrello.clearCarrello();
       cartRepo.save(carrello);
   }

   public Carrello loadOrCreateCarrello(UUID buyerId)  {

        Acquirente buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Acquirente non trovato"));

        return cartRepo.findByBuyerId(buyerId)
               .orElseGet(() -> {
                   Carrello nuovoCarrello = new Carrello();
                   nuovoCarrello.setBuyer(buyer);
                   return cartRepo.save(nuovoCarrello);
                });
   }

    // Metodo helper per ottenere l'entità Carrello (rinominato per chiarezza)
    public Carrello getCarrelloEntity(UUID buyerId) {
        return loadOrCreateCarrello(buyerId);
    }

   public CarrelloResponseDTO getCarrello(UUID buyerId)  {
        Carrello carrello = getCarrelloEntity(buyerId);
        return  mapToCarrelloResponseDTO(carrello);
   }

   public Ordine buyCart(UUID buyerId) {

        Carrello carrello = getCarrelloEntity(buyerId);

        List<ItemCarrello > listOfItems = carrello.getProducts();

       if(cartIsEmpty(buyerId)) {
           throw new ProductNotFoundException("Il carrello è vuoto");
       }

       for (ItemCarrello item : carrello.getProducts()) {
           Prodotto prodotto = productService.getById(item.getProduct().getId())
                   .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));


           productService.riduciQuantita(prodotto.getId(), item.getQuantity());
       }

       Ordine ordine = new Ordine();
       ordine.setBuyer(buyerRepo.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Acquirente non trovato")));
       ordine.setDataOrdine(LocalDate.now());
       ordineRepo.save(ordine);

       List<ItemOrdine> itemsOrdine = listOfItems.stream()
               .map(item -> new ItemOrdine(item.getIdItem() ,item.getProduct().getName(), item.getProduct().getPrice() ,item.getQuantity()))
               .toList();


       for (ItemOrdine itemOrdine : itemsOrdine) {
           ordine.addItem(itemOrdine);
       }



         //ordine.setTotale(carrello.getTotalPrice());

       clearCarrello(buyerId);
       return ordine;
   }


    public boolean cartIsEmpty(UUID buyerId) {
        Carrello carrello = getCarrelloEntity(buyerId);
        return carrello.getProducts().isEmpty();
    }
}
