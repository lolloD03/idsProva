package com.filiera.services;

import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.User;
import com.filiera.repository.CrudRepository;

import com.filiera.model.products.StatoProdotto;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override public Prodotto createProduct(Venditore seller, String name, String descrizione, double price, int quantity) {

        if(userRepo.findById(seller.getId()).isEmpty()){
            throw new IllegalArgumentException("Il venditore con ID " + seller.getId() + " non esiste.");
        }

        Prodotto prodotto = new Prodotto();
        prodotto.setId(UUID.randomUUID());
        prodotto.setName(name);
        prodotto.setDescription(descrizione);
        prodotto.setPrice(price);
        prodotto.setAvailableQuantity(quantity);
        prodotto.setState(StatoProdotto.IN_ATTESA_DI_APPROVAZIONE);
        prodotto.setSeller(seller);

        Prodotto prodottoSalvato = prodRepo.save(prodotto);

        return prodottoSalvato;
}

    @Override
    public Prodotto updateProduct(Prodotto updatedProduct) {

        Prodotto actualProduct = prodRepo.findById(updatedProduct.getId())
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + updatedProduct.getId()));

        actualProduct.setName(updatedProduct.getName());
        actualProduct.setDescription(updatedProduct.getDescription());
        actualProduct.setPrice(updatedProduct.getPrice());
        actualProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());

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


}
