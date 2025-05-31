package com.filiera.services;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.model.Products.StatoProdotto;
import java.util.List;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private final InMemoryProductRepository repo;
    private final UserServiceImpl userService;
    public ProductServiceImpl(InMemoryProductRepository repo, UserServiceImpl userService) { this.repo = repo;
        this.userService = userService;
    }
    //@Override public Prodotto createProduct(Prodotto product) { return repo.save(product); }
    @Override public List<Prodotto> listAll() { return repo.findAll(); }
    @Override public Prodotto getById(UUID id) { return repo.findById(id).orElse(null); }

    @Override public Prodotto createProduct(Venditore seller, String name, String descrizione, double price, int quantity) {

        if (!existsById(seller.getId())) {
            throw new IllegalArgumentException("Il venditore con ID " + seller.getId() + " non esiste.");
        }

        Prodotto prodotto = new Prodotto();
        prodotto.setName(name);
        prodotto.setDescription(descrizione);
        prodotto.setPrice(price);
        prodotto.setAvailableQuantity(quantity);
        prodotto.setState(StatoProdotto.IN_ATTESA_DI_APPROVAZIONE);
        prodotto.setSeller(seller);

        Prodotto prodottoSalvato = repo.save(prodotto);

        return prodottoSalvato;
}

    public boolean existsById(UUID id) {
    return userService.findById(id) != null;
    }
}
