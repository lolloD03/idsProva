package com.filiera.services;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.model.Products.StatoProdotto;
import com.filiera.repository.InMemoryUserRepository;

import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private final InMemoryProductRepository prodRepo;
    private final UserServiceImpl userService;
    private final InMemoryUserRepository userRepo;

    public ProductServiceImpl(InMemoryProductRepository repo, UserServiceImpl userService, InMemoryUserRepository userRepository)
    {
        this.prodRepo = repo;
        this.userService = userService;
        this.userRepo = userRepository;
    }
    //@Override public Prodotto createProduct(Prodotto product) { return repo.save(product); }
    @Override public List<Prodotto> listAll() { return prodRepo.findAll(); }
    @Override public Prodotto getById(UUID id) { return prodRepo.findById(id).orElse(null); }

    @Override public Prodotto createProduct(Venditore seller, String name, String descrizione, double price, int quantity) {

        if(userRepo.findById(seller.getId()).isEmpty()){
            throw new IllegalArgumentException("Il venditore con ID " + seller.getId() + " non esiste.");
        }

        Prodotto prodotto = new Prodotto();
        prodotto.setName(name);
        prodotto.setDescription(descrizione);
        prodotto.setPrice(price);
        prodotto.setAvailableQuantity(quantity);
        prodotto.setState(StatoProdotto.IN_ATTESA_DI_APPROVAZIONE);
        prodotto.setSeller(seller);

        Prodotto prodottoSalvato = prodRepo.save(prodotto);

        return prodottoSalvato;
}

    public Prodotto updateProduct(Prodotto updatedProduct) {

        Prodotto actualProduct = prodRepo.findById(updatedProduct.getId())
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + updatedProduct.getId()));

        actualProduct.setName(updatedProduct.getName());
        actualProduct.setDescription(updatedProduct.getDescription());
        actualProduct.setPrice(updatedProduct.getPrice());
        actualProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());

        return prodRepo.save(actualProduct);
    }

    public void deleteProduct(Prodotto prodotto) {

        if (prodRepo.findById(prodotto.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con ID " + prodotto.getId() + " non esiste.");
        }

        prodRepo.deleteById(prodotto.getId());

    }


    @Override
    public List<Prodotto> getApprovedProducts() {
        return prodRepo.findByState(StatoProdotto.APPROVATO);
    }


    public boolean existsById(UUID id) {
    return userService.findById(id) != null;
    }
}
