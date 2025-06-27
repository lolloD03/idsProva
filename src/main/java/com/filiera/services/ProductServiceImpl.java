package com.filiera.services;

import com.filiera.exception.*;
import com.filiera.model.dto.ProdottoRequestDTO;
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
    public Prodotto createProduct(ProdottoRequestDTO productRequestDTO, UUID sellerId) {
        logger.info("Creating new product from request DTO for vendor: {}", sellerId);

        // Find seller
        Venditore seller = vendRepo.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with Id: " + sellerId));

        // Create and save product
        Prodotto product = Prodotto.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .availableQuantity(productRequestDTO.getQuantity())
                .certification(productRequestDTO.getCertification())
                .expirationDate(productRequestDTO.getExpirationDate()) // Se presente
                .seller(seller)
                .build();

        Prodotto savedProduct = prodRepo.save(product);
        seller.addProduct(savedProduct);

        logger.info("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }

    /*
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


     */
    @Override
    public Prodotto updateProduct(UUID productId, ProdottoRequestDTO productRequestDTO, UUID sellerId) {
        logger.info("Updating product with ID : {} for seller: {}", productId, sellerId);

        // 2. Trova il prodotto esistente
        Prodotto existingProduct = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        // 3. Verifica la proprietà del venditore
        // È fondamentale assicurarsi che solo il venditore proprietario possa modificare il prodotto.
        if (!existingProduct.getSeller().getId().equals(sellerId)) {
            logger.warn("Attempt not authorized updating product {} by seller {}. Product belongs to seller {}.",
                    productId, sellerId, existingProduct.getSeller().getId());
            throw new InvalidUserTypeException("Seller with ID " + sellerId +
                    " is not authorizet to update product with ID " + productId);
        }

        // 4. Aggiorna i campi del prodotto
        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setDescription(productRequestDTO.getDescription());
        existingProduct.setPrice(productRequestDTO.getPrice());
        existingProduct.setAvailableQuantity(productRequestDTO.getQuantity());
        existingProduct.setCertification(productRequestDTO.getCertification());
        existingProduct.setExpirationDate(productRequestDTO.getExpirationDate());
        existingProduct.setState(StatoProdotto.PENDING_APPROVAL); // Imposta lo stato a "In attesa di approvazione"
        // 5. Salva il prodotto aggiornato
        Prodotto updatedProduct = prodRepo.save(existingProduct);
        logger.info("Prodotto aggiornato con successo con id: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public void deleteProduct(UUID productId) {


        logger.info("Deleting product with id: {}", productId);

        // Check if product exists
        if (!prodRepo.existsById(productId)) {
            throw new ProductNotFoundException("Product with ID  " + productId + " doesn't exist.");
        }

        prodRepo.deleteById(productId);
        logger.info("Product deleted successfully with id: {}", productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prodotto> getApprovedProducts() {
        logger.debug("Retrieving approved products");
        return prodRepo.findByState(StatoProdotto.APPROVED);
    }

    @Override
    public void decreaseQuantity(UUID productId, int quantity) {
        logger.info("Reducing quantity for product: {} by {}", productId, quantity);

        // Validate quantity
        if (quantity <= 0) {
            throw new InsufficientQuantityException("Quantity to decrease must be greater than zero.");
        }

        // Find product
        Prodotto product = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " doesn't exist."));

        // Check available quantity
        if (product.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Unavailable quantity for product with ID " + productId +
                    ". Available: " + product.getAvailableQuantity() + ", Request: " + quantity);
        }

        // Update quantity
        int newQuantity = product.getAvailableQuantity() - quantity;
        product.setAvailableQuantity(newQuantity);

        // Update state if necessary
        if (newQuantity == 0) {
            product.setState(StatoProdotto.OUT_OF_STOCK);
            logger.info("Product {} is now out of stock", productId);
        }

        prodRepo.save(product);
        logger.info("Quantity reduced successfully for product: {}", productId);
    }

    public Prodotto checkProductState(UUID productId) {

        Prodotto product = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " doesn't exist."));

        if(product.getState()==StatoProdotto.PENDING_APPROVAL)
            throw new ProductStateException("Can't buy a product with state 'pending_approval'");

        if(product.getState()==StatoProdotto.OUT_OF_STOCK)
            throw new ProductStateException("Can't buy a product with state 'out of stock'");

        if(product.getState()==StatoProdotto.REJECTED)
            throw new ProductStateException("Can't buy a product with state 'rejected'");

        return product;
    }


}