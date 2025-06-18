package com.filiera.services;

import com.filiera.exception.InsufficientQuantityException;
import com.filiera.exception.ProductNotFoundException;
import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;
import org.springframework.transaction.annotation.Transactional;
import com.filiera.model.products.StatoProdotto;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryVenditoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final InMemoryProductRepository prodRepo;
    private final InMemoryVenditoreRepository vendRepo;

    @Autowired
    public ProductServiceImpl(InMemoryProductRepository prodRepo, InMemoryVenditoreRepository vendRepo) {
        this.prodRepo = prodRepo;
        this.vendRepo = vendRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prodotto> listAll() {
        logger.debug("Retrieving all products");
        return prodRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prodotto> getById(UUID id) {
        logger.debug("Retrieving product with id: {}", id);
        return prodRepo.findById(id);
    }

    @Override
    public Prodotto createProduct(UUID sellerId, String name, String descrizione, double price, int quantity, String certification) {
        logger.info("Creating new product for seller: {}", sellerId);

        // Validate input parameters
        validateProductInput(name, descrizione, price, quantity, certification);

        // Find seller
        Venditore venditore = vendRepo.findById(sellerId)
                .orElseThrow(() -> new ProductNotFoundException("Venditore non trovato con id: " + sellerId));

        // Create and save product
        Prodotto prodotto = Prodotto.creaProdotto(name, descrizione, price, quantity, venditore, certification);
        Prodotto savedProduct = prodRepo.save(prodotto);

        logger.info("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Prodotto updateProduct(UUID prodottoId, String name, String descrizione, double price, int quantity) {
        logger.info("Updating product with id: {}", prodottoId);

        // Validate input parameters
        validateProductInput(name, descrizione, price, quantity, null);

        // Find existing product
        Prodotto actualProduct = prodRepo.findById(prodottoId)
                .orElseThrow(() -> new ProductNotFoundException("Prodotto non trovato con id: " + prodottoId));

        // Update product
        actualProduct.aggiornaProdotto(name, descrizione, price, quantity);
        Prodotto updatedProduct = prodRepo.save(actualProduct);

        logger.info("Product updated successfully with id: {}", prodottoId);
        return updatedProduct;
    }

    @Override
    public void deleteProduct(UUID prodottoId) {
        logger.info("Deleting product with id: {}", prodottoId);

        // Check if product exists
        if (!prodRepo.existsById(prodottoId)) {
            throw new ProductNotFoundException("Il prodotto con ID " + prodottoId + " non esiste.");
        }

        prodRepo.deleteById(prodottoId);
        logger.info("Product deleted successfully with id: {}", prodottoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prodotto> getApprovedProducts() {
        logger.debug("Retrieving approved products");
        return prodRepo.findByState(StatoProdotto.APPROVATO);
    }

    @Override
    public void riduciQuantità(UUID prodottoId, int quantity) {
        logger.info("Reducing quantity for product: {} by {}", prodottoId, quantity);

        // Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantità da ridurre deve essere maggiore di zero.");
        }

        // Find product
        Prodotto prodotto = prodRepo.findById(prodottoId)
                .orElseThrow(() -> new ProductNotFoundException("Il prodotto con ID " + prodottoId + " non esiste."));

        // Check available quantity
        if (prodotto.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Quantità insufficiente per il prodotto con ID " + prodottoId +
                    ". Disponibile: " + prodotto.getAvailableQuantity() + ", Richiesta: " + quantity);
        }

        // Update quantity
        int newQuantity = prodotto.getAvailableQuantity() - quantity;
        prodotto.setAvailableQuantity(newQuantity);

        // Update state if necessary
        if (newQuantity == 0) {
            prodotto.setState(StatoProdotto.ESAURITO);
            logger.info("Product {} is now out of stock", prodottoId);
        }

        prodRepo.save(prodotto);
        logger.info("Quantity reduced successfully for product: {}", prodottoId);
    }

    // Overloaded method for backward compatibility
    public void riduciQuantità(Prodotto prodotto, int quantity) {
        riduciQuantità(prodotto.getId(), quantity);
    }

    private void validateProductInput(String name, String descrizione, double price, int quantity, String certification) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto");
        }
        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new IllegalArgumentException("La descrizione del prodotto non può essere vuota");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore di zero");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantità deve essere maggiore di zero");
        }
        if (certification != null && certification.trim().isEmpty()) {
            throw new IllegalArgumentException("La certificazione non può essere vuota se fornita");
        }
    }
}