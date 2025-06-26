package com.filiera.services;

import com.filiera.exception.ProductNotFoundException;
import com.filiera.model.payment.Carrello;
import com.filiera.model.payment.ItemCarrello;
import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import com.filiera.repository.InMemoryAcquirenteRepository;
import com.filiera.repository.InMemoryCarrelloRepository;
import com.filiera.repository.InMemoryOrdineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.filiera.model.payment.Ordine;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


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

    public Carrello addProduct(UUID prod , int quantity , UUID buyerId) {


        Prodotto prodotto = productService.getById(prod)
                .orElseThrow(() -> new ProductNotFoundException("Il prodotto con id " + prod + " non esiste."));

        Carrello carrello = getCarrello(buyerId);

        carrello.addProduct(prodotto , quantity);

        cartRepo.save(carrello);
        return carrello;
    }

    public Carrello removeProduct(UUID prod , int quantity , UUID buyerId) {
        if(productService.getById(prod).isEmpty()) {
            throw new RuntimeException("Il prodotto con id " + prod + " non esiste.");
        }

        Carrello carrello = getCarrello(buyerId);

        if(cartIsEmpty(buyerId)) {
            throw new RuntimeException("Il carrello è vuoto");
        }

        carrello.removeProduct(productService.getById(prod).get() , quantity);

        cartRepo.save(carrello);
        return carrello;
    }

    public StringBuilder getInvoice(UUID buyerId) {

        Carrello carrello = getCarrello(buyerId);

        double totalInvoice = carrello.getTotalPrice();

        StringBuilder sb = new StringBuilder();

        sb.append("Fattura:\n");
        for(ItemCarrello prod : carrello.getProducts()) {
            sb.append("Prodotto: ").append(prod.getProduct().getName()).append("\n");
            sb.append("Prezzo: ").append(prod.getProduct().getPrice()).append("\n");
            sb.append("Quantità: ").append(prod.getQuantity()).append("\n");
        }
        sb.append("Totale: ").append(totalInvoice).append("\n");
        return sb;
    }



   public void clearCarrello(UUID buyerId) {

        Carrello carrello = getCarrello(buyerId);

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

   public Carrello getCarrello(UUID buyerId)  {
        return loadOrCreateCarrello(buyerId);
   }

   public Ordine buyCart(UUID buyerId) {

        Carrello carrello = getCarrello(buyerId);

        List<ItemCarrello > listOfItems = carrello.getProducts();

       if(cartIsEmpty(buyerId)) {
           throw new ProductNotFoundException("Il carrello è vuoto");
       }

       for (ItemCarrello item : carrello.getProducts()) {
           Prodotto prodotto = productService.getById(item.getProduct().getId())
                   .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));


           productService.decreaseQuantity(prodotto.getId(), item.getQuantity());
       }

       Ordine ordine = Ordine.builder()
                        .buyer(buyerRepo.getById(buyerId))
                        .items(listOfItems)
                        .totale(carrello.getTotalPrice())
                        .dataOrdine (LocalDate.now())
                        .build();

       clearCarrello(buyerId);

       ordineRepo.save(ordine);
       return ordine;
   }


    public boolean cartIsEmpty(UUID buyerId) {
        Carrello carrello = getCarrello(buyerId);
        return carrello.getProducts().isEmpty();
    }
}
