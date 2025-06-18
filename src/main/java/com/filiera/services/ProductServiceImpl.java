package com.filiera.services;

import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.User;
import com.filiera.repository.CrudRepository;

import com.filiera.model.products.StatoProdotto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final CrudRepository<Prodotto, UUID> prodRepo;

    private final CrudRepository<User, UUID> userRepo;

    public ProductServiceImpl(CrudRepository<Prodotto, UUID> repo,  CrudRepository<User , UUID> userRepository)
    {
        this.prodRepo = repo;
        this.userRepo = userRepository;
    }

    @Override public List<Prodotto> listAll() { return prodRepo.findAll(); }

    @Override public Optional<Prodotto> getById(UUID id) {
        return prodRepo.findById(id);
    }

    @Override
    public Prodotto createProduct(Venditore seller, String name, String descrizione, double price, int quantity, String certification) {
        if(userRepo.findById(seller.getId()).isEmpty()){
            throw new IllegalArgumentException("Il venditore con ID " + seller.getId() + " non esiste.");
        }
        Prodotto prodotto = Prodotto.creaProdotto(name, descrizione, price, quantity, seller, certification);
        return prodRepo.save(prodotto);
    }


    @Override
    public Prodotto updateProduct(Prodotto updatedProduct) {
        Prodotto actualProduct = prodRepo.findById(updatedProduct.getId())
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + updatedProduct.getId()));
        actualProduct.aggiornaProdotto(
            updatedProduct.getName(),
            updatedProduct.getDescription(),
            updatedProduct.getPrice(),
            updatedProduct.getAvailableQuantity()
        );
        return prodRepo.save(actualProduct);
    }

    @Override
    public void deleteProduct(Prodotto prodotto) {

        if (prodRepo.findById(prodotto.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con ID " + prodotto.getId() + " non esiste.");
        }

        prodRepo.deleteById(prodotto.getId());

    }


    @Override
    public List<Prodotto> getApprovedProducts() {
        return prodRepo.findAll().stream()
                .filter(p -> p.getState() == StatoProdotto.APPROVATO)
                .toList();
    }


    public void riduciQuantità(Prodotto prodotto, int quantity) {
        if (prodRepo.findById(prodotto.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con ID " + prodotto.getId() + " non esiste.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantità da ridurre deve essere maggiore di zero.");
        }

        if (prodotto.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Quantità insufficiente per il prodotto con ID " + prodotto.getId());
        }

        prodotto.setAvailableQuantity(prodotto.getAvailableQuantity() - quantity);

        if (prodotto.getAvailableQuantity() == 0) {
            prodotto.setState(StatoProdotto.ESAURITO);
        }

         // Salva le modifiche al prodotto
        prodRepo.save(prodotto);
    }

}
