package com.filiera.services;

import com.filiera.model.payment.Carrello;
import com.filiera.model.payment.ItemCarrello;
import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import com.filiera.repository.InMemoryAcquirenteRepository;
import com.filiera.repository.InMemoryCarrelloRepository;
import com.filiera.repository.InMemoryProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class CarrelloServiceImpl {

    private final ProductService productService;

    private final InMemoryCarrelloRepository cartRepo;

    private final InMemoryAcquirenteRepository buyerRepo;

    public CarrelloServiceImpl(ProductService productService , InMemoryCarrelloRepository cartRepo, InMemoryAcquirenteRepository buyerRepo ) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.buyerRepo = buyerRepo;
    }

    public List<ItemCarrello> addProduct(UUID prod , int quantity , UUID buyerId) {
        if(productService.getById(prod).isEmpty()) {
            throw new RuntimeException("Il prodotto con id " + prod + " non esiste.");
        }
        if(productService.getById(prod).get().getAvailableQuantity() <= 0) {
            throw new RuntimeException("Il prodotto con id " + prod+ " non è disponibile.");
        }

        Carrello carrello = getCarrello(buyerId);

        carrello.addProduct(productService.getById(prod).get() , quantity);

        cartRepo.save(carrello);
        return carrello.getProducts();
    }

    public List<ItemCarrello> removeProduct(UUID prod , int quantity , UUID buyerId) {
        if(productService.getById(prod).isEmpty()) {
            throw new RuntimeException("Il prodotto con id " + prod + " non esiste.");
        }

        Carrello carrello = getCarrello(buyerId);

        carrello.removeProduct(productService.getById(prod).get() , quantity);

        cartRepo.save(carrello);
        return carrello.getProducts();
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
            sb.append("Prezzo totale: ").append(prod.getTotal()).append("\n");
        }
        return sb;
    }



   public void clearCarrello(UUID buyerId) {

        Carrello carrello = getCarrello(buyerId);

        if(carrello.getProducts().isEmpty()){
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

}
