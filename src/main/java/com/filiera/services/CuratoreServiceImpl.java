package com.filiera.services;

import com.filiera.exception.CuratorNotFoundException;
import com.filiera.exception.InvalidUserTypeException;
import com.filiera.exception.ProductNotFoundException;
import com.filiera.exception.ProductNotPendingException;
import com.filiera.model.administration.Curatore;
import com.filiera.model.products.Prodotto;
import com.filiera.model.products.StatoProdotto;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CuratoreServiceImpl {

    private final InMemoryProductRepository productRepository;
    private final InMemoryUserRepository userRepository;

    @Autowired
    public CuratoreServiceImpl(InMemoryProductRepository productRepository, InMemoryUserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<Prodotto> getPendingProducts() {
        log.debug("Retrieving all pending products");
        return productRepository.findByState(StatoProdotto.IN_ATTESA_DI_APPROVAZIONE);
    }


    public Prodotto approveProduct(UUID prodottoId, UUID curatoreId) {
        log.info("Approving product {} by curator {}", prodottoId, curatoreId);

        // Validate input parameters
        validateIds(prodottoId, curatoreId);

        // Find and validate product
        Prodotto prodotto = findProductById(prodottoId);
        validateProductForApproval(prodotto);

        // Find and validate curator
        Curatore curatore = findCuratorById(curatoreId);

        // Approve product
        prodotto.approveBy(curatore);
        Prodotto approvedProduct = productRepository.save(prodotto);

        log.info("Product {} approved successfully by curator {}", prodottoId, curatoreId);
        return approvedProduct;
    }


    public Prodotto rejectProduct(UUID prodottoId, UUID curatoreId) {
        log.info("Rejecting product {} by curator {}", prodottoId, curatoreId);

        // Validate input parameters
        validateIds(prodottoId, curatoreId);

        // Find and validate product
        Prodotto prodotto = findProductById(prodottoId);
        validateProductForApproval(prodotto);

        // Find and validate curator
        Curatore curatore = findCuratorById(curatoreId);

        // Reject product
        prodotto.rejectBy(curatore);
        Prodotto rejectedProduct = productRepository.save(prodotto);

        log.info("Product {} rejected successfully by curator {}", prodottoId, curatoreId);
        return rejectedProduct;
    }


    @Transactional(readOnly = true)
    public List<Prodotto> getProductsByState(StatoProdotto stato) {
        log.debug("Retrieving products with state: {}", stato);
        return productRepository.findByState(stato);
    }


    @Transactional(readOnly = true)
    public List<Prodotto> getApprovedProducts() {
        log.debug("Retrieving approved products");
        return productRepository.findByState(StatoProdotto.APPROVATO);
    }


    @Transactional(readOnly = true)
    public List<Prodotto> getRejectedProducts() {
        log.debug("Retrieving rejected products");
        return productRepository.findByState(StatoProdotto.RIFIUTATO);
    }

    // Private helper methods
    private void validateIds(UUID prodottoId, UUID curatoreId) {
        if (prodottoId == null) {
            throw new IllegalArgumentException("L'ID del prodotto non può essere null");
        }
        if (curatoreId == null) {
            throw new IllegalArgumentException("L'ID del curatore non può essere null");
        }
    }

    private Prodotto findProductById(UUID prodottoId) {
        return productRepository.findById(prodottoId)
                .orElseThrow(() -> new ProductNotFoundException("Prodotto non trovato con id: " + prodottoId));
    }

    private void validateProductForApproval(Prodotto prodotto) {
        if (prodotto.getState() != StatoProdotto.IN_ATTESA_DI_APPROVAZIONE) {
            throw new ProductNotPendingException(
                    "Il prodotto con ID " + prodotto.getId() + " non è in attesa di approvazione. Stato attuale: " + prodotto.getState()
            );
        }
    }

    private Curatore findCuratorById(UUID curatoreId) {
        User user = userRepository.findById(curatoreId)
                .orElseThrow(() -> new CuratorNotFoundException("Il curatore con ID " + curatoreId + " non esiste."));

        if (!(user instanceof Curatore)) {
            throw new InvalidUserTypeException("L'utente con ID " + curatoreId + " non è un curatore.");
        }

        return (Curatore) user;
    }
}