package com.filiera.services;

import com.filiera.model.administration.Curatore;
import com.filiera.model.products.Prodotto;
import com.filiera.model.products.StatoProdotto;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class CuratoreServiceImpl {

    private final InMemoryProductRepository productRepository;
    private final InMemoryUserRepository userRepository;

    @Autowired
    public CuratoreServiceImpl(InMemoryProductRepository productRepository, InMemoryUserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Prodotto> getPendingProducts() {
        List<Prodotto> pendingProducts = productRepository.findByState(StatoProdotto.IN_ATTESA_DI_APPROVAZIONE);
        return pendingProducts;
    }



    public Prodotto approveProduct(UUID prodottoId, UUID curatoreId) {
        Prodotto prodotto = productRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + prodottoId));
        if (prodotto.getState() != StatoProdotto.IN_ATTESA_DI_APPROVAZIONE) {
            throw new IllegalArgumentException("Il prodotto non è in attesa di approvazione.");
        }
        if (userRepository.findById(curatoreId).isEmpty()) {
            throw new IllegalArgumentException("Il curatore con ID " + curatoreId + " non esiste.");
        }
        Curatore curatore = (Curatore) userRepository.findById(curatoreId).get();
        prodotto.approveBy(curatore);
        return productRepository.save(prodotto);
    }

    public Prodotto rejectProduct(UUID prodottoId, UUID curatoreId) {
        Prodotto prodotto = productRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + prodottoId));
        if (prodotto.getState() != StatoProdotto.IN_ATTESA_DI_APPROVAZIONE) {
            throw new IllegalArgumentException("Il prodotto non è in attesa di approvazione.");
        }
        if (userRepository.findById(curatoreId).isEmpty()) {
            throw new IllegalArgumentException("Il curatore con ID " + curatoreId + " non esiste.");
        }
        Curatore curatoreObj = (Curatore) userRepository.findById(curatoreId).get();
        prodotto.rejectBy(curatoreObj);
        return productRepository.save(prodotto);
    }
}
