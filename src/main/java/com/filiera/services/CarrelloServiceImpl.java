package com.filiera.services;

import com.filiera.model.payment.Carrello;
import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import com.filiera.repository.InMemoryCarrelloRepository;
import com.filiera.repository.InMemoryProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class CarrelloServiceImpl {

    private final ProductService productService;
    private final InMemoryCarrelloRepository cartRepo;
    Carrello carrello;

    @Autowired
    public CarrelloServiceImpl( ProductService productService , InMemoryCarrelloRepository cartRepo) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.carrello = cartRepo.findById(carrello.getBuyer().getId())  // oppure id del carrello utente
                .orElseGet(() -> new Carrello());
    }

    public List<Prodotto> addProduct(Prodotto prod) {
        if(productService.getById(prod.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con id " + prod.getId() + " non esiste.");
        }
        if(prod.getAvailableQuantity() == 0) {
            throw new RuntimeException("Il prodotto con id " + prod.getId() + " non è disponibile.");
        }

        this.carrello.addProduct(prod);

        cartRepo.save(carrello);
        return List.copyOf(carrello.getProducts());
    }

    public List<Prodotto> removeProduct(Prodotto prod) {
        if(productService.getById(prod.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con id " + prod.getId() + " non esiste.");
        }

        carrello.removeProduct(prod);

        cartRepo.save(carrello);
        return List.copyOf(carrello.getProducts());
    }

    public StringBuilder getInvoice(Carrello carrello) {


        double totalInvoice = carrello.getTotalPrice();

        StringBuilder sb = new StringBuilder();

        sb.append("Fattura:\n");
        for(Prodotto prod : carrello.getProducts()) {
            sb.append("Prodotto: ").append(prod.getName()).append("\n");
            sb.append("Prezzo: ").append(prod.getPrice()).append("\n");
            sb.append("Quantità: ").append(prod.getAvailableQuantity()).append("\n");
            sb.append("Prezzo totale: ").append(prod.getPrice() * prod.getAvailableQuantity()).append("\n");
        }
        return sb;
    }



   public void clearCarrello() throws Exception {

        if(carrello.getProducts().isEmpty()){
           throw new Exception("Il carrello è vuoto");
       }

       carrello.clearCarrello();
       cartRepo.save(carrello);
   }

   public Carrello getCarrello(){
        return this.carrello;
   }

}
