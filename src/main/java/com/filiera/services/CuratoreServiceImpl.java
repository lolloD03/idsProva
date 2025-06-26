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


    public Prodotto approveProduct(UUID productId, UUID curatorId) {
        log.info("Approving product {} by curator {}", productId , curatorId);

        // Validate input parameters
        validateIds(productId , curatorId);

        // Find and validate product
        Prodotto prodotto = findProductById(productId);
        validateProductForApproval(prodotto);

        // Find and validate curator
        Curatore curatore = findCuratorById(curatorId);

        // Approve product
        prodotto.approveBy(curatore);
        Prodotto approvedProduct = productRepository.save(prodotto);

        log.info("Product {} approved successfully by curator {}", productId , curatorId);
        return approvedProduct;
    }


    public Prodotto rejectProduct(UUID productId, UUID curatoreId) {
        log.info("Rejecting product {} by curator {}", productId, curatoreId);

        // Validate input parameters
        validateIds(productId, curatoreId);


        // Find and validate product
        Prodotto product = findProductById(productId);
        validateProductForApproval(product);

        // Find and validate curator
        Curatore curator = findCuratorById(curatoreId);

        // Reject product
        product.rejectBy(curator);
        Prodotto rejectedProduct = productRepository.save(product);

        log.info("Product {} rejected successfully by curator {}", productId, curatoreId);
        return rejectedProduct;
    }


    @Transactional(readOnly = true)
    public List<Prodotto> getProductsByState(StatoProdotto state) {
        log.debug("Retrieving products with state: {}", state);
        return productRepository.findByState(state);
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
    private void validateIds(UUID productId, UUID curatorId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product id can't be null");
        }
        if (curatorId == null) {
            throw new IllegalArgumentException("Product id can't be null");
        }
    }

    private Prodotto findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Prodotto non trovato con id: " + productId));
    }

    private void validateProductForApproval(Prodotto product) {
        if (product.getState() != StatoProdotto.IN_ATTESA_DI_APPROVAZIONE) {
            throw new ProductNotPendingException(
                    "Il prodotto con ID " + product.getId() + " non è in attesa di approvazione. Stato attuale: " + product.getState()
            );
        }
    }

    private Curatore findCuratorById(UUID curatorId) {
        User user = userRepository.findById(curatorId)
                .orElseThrow(() -> new CuratorNotFoundException("Il curatore con ID " + curatorId + " non esiste."));

        if (!(user instanceof Curatore)) {
            throw new InvalidUserTypeException("L'utente con ID " + curatorId + " non è un curatore.");
        }

        return (Curatore) user;
    }
}