package com.filiera.services;

import com.filiera.model.Products.Carrello;
import com.filiera.model.Products.Prodotto;
import com.filiera.model.users.Acquirente;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;

import java.util.List;

public class AcquirenteServiceImpl implements UserService {

    private final Acquirente buyer;
    private Carrello carrello;
    private final InMemoryProductRepository prodRepo;
    private final ProductService productService;

    public AcquirenteServiceImpl(InMemoryProductRepository prodRepo, ProductServiceImpl productService) {
       this.prodRepo = prodRepo;
       this.productService = productService;
       this.carrello = new Carrello();
       this.buyer = new Acquirente();

    }

    public List<Prodotto> addProduct(Prodotto prod) {
        if(!productService.existsById(prod.getId())) {throw new RuntimeException("Il prodotto con id " + prod.getId() + " non esiste.");}

        carrello.addProduct(prod);
        return carrello.getProducts();
    }

    public List<Prodotto> removeProduct(Prodotto prod) {
        if(!productService.existsById(prod.getId())) {throw new RuntimeException("Il prodotto con id " + prod.getId() + " non esiste.");}
        carrello.removeProduct(prod);
        return carrello.getProducts();
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

   public void clearCarrello(Carrello carrello){
       if (carrello.getProducts().isEmpty()) {
           throw new RuntimeException("Il carrello è già vuoto.");
       }

       carrello.clearCarrello();
   }

}
