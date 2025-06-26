package com.filiera.services;

import com.filiera.exception.InsufficientQuantityException;
import com.filiera.exception.ProductNotFoundException;
import com.filiera.exception.ProductStateException;
import com.filiera.exception.UnauthorizedOperationException;
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

        // Validate input parameters
        validateProductInput(productRequestDTO);

        // Find seller
        Venditore venditore = vendRepo.findById(sellerId)
                .orElseThrow(() -> new ProductNotFoundException("Seller not found with id: " +  sellerId));

        // Create and save product
        Prodotto prodotto = Prodotto.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .availableQuantity(productRequestDTO.getQuantity())
                .certification(productRequestDTO.getCertification())
                .expirationDate(productRequestDTO.getExpirationDate()) // Se presente
                .seller(venditore)
                .build();

        Prodotto savedProduct = prodRepo.save(prodotto);
        venditore.addProduct(savedProduct);

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
        logger.info("Updating product with id: {} for seller: {}", productId, sellerId);

        // 1. Valida i parametri di input per l'aggiornamento
        validateProductInput(productRequestDTO);

        // 2. Trova il prodotto esistente
        Prodotto existingProduct = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        // 3. Verifica la proprietà del venditore
        // È fondamentale assicurarsi che solo il venditore proprietario possa modificare il prodotto.
        if (!existingProduct.getSeller().getId().equals(sellerId)) {
            logger.warn("Attempt not authorized updating product : {} by seller {}. Product doesn't belong to seller {}.",
                    productId, sellerId, existingProduct.getSeller().getId());
            throw new UnauthorizedOperationException("Seller with id " + sellerId +
                    " is not authorized to update product with id : " + productId);
        }

        // 4. Aggiorna i campi del prodotto
        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setDescription(productRequestDTO.getDescription());
        existingProduct.setPrice(productRequestDTO.getPrice());
        existingProduct.setAvailableQuantity(productRequestDTO.getQuantity());
        existingProduct.setCertification(productRequestDTO.getCertification());
        existingProduct.setExpirationDate(productRequestDTO.getExpirationDate());

        // 5. Salva il prodotto aggiornato
        Prodotto updatedProduct = prodRepo.save(existingProduct);
        logger.info("Succesfully updated product with id : {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public void deleteProduct(UUID productId) {


        logger.info("Deleting product with id: {}", productId);

        // Check if product exists
        if (!prodRepo.existsById(productId)) {
            throw new ProductNotFoundException("Product with id " + productId + " doesn't exist.");
        }

        prodRepo.deleteById(productId);
        logger.info("Product deleted successfully with id: {}", productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prodotto> getApprovedProducts() {
        logger.debug("Retrieving approved products");
        return prodRepo.findByState(StatoProdotto.APPROVATO);
    }

    @Override
    public void decreaseQuantity(UUID productId, int quantity) {
        logger.info("Reducing quantity for product: {} by {}", productId, quantity);

        // Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to decrease must be greater than zero.");
        }

        // Find product
        Prodotto product = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " doesn't exist."));

        // Check available quantity
        if (product.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Unavailable quantity for product with id " + productId +
                    ". Available: " + product.getAvailableQuantity() + ", Request: " + quantity);
        }

        // Update quantity
        int newQuantity = product.getAvailableQuantity() - quantity;
        product.setAvailableQuantity(newQuantity);

        // Update state if necessary
        if (newQuantity == 0) {
            product.setState(StatoProdotto.ESAURITO);
            logger.info("Product {} is now out of stock", productId);
        }

        prodRepo.save(product);
        logger.info("Quantity reduced successfully for product: {}", productId);
    }

    public Prodotto checkProductState(UUID productId) {

        Prodotto product = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " doesn't exist."));

        if(product.getState()==StatoProdotto.IN_ATTESA_DI_APPROVAZIONE)
            throw new ProductStateException("Can't buy a product with state 'appending_approval'");

        if(product.getState()==StatoProdotto.ESAURITO)
            throw new ProductStateException("Can't buy a product with state 'out_of_stock'");

        if(product.getState()==StatoProdotto.RIFIUTATO)
            throw new ProductStateException("Can't buy a product with state 'rejected'");

        return product;
    }

    private void validateProductInput(ProdottoRequestDTO prodottoRequestDTO) {
        if (prodottoRequestDTO == null) {
            throw new IllegalArgumentException("Product can't be null.");
        }

        if (prodottoRequestDTO.getName() == null || prodottoRequestDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (prodottoRequestDTO.getDescription() == null || prodottoRequestDTO.getDescription().isBlank()) {
            throw new IllegalArgumentException("Product description can't be null or empty.");
        }
        if (prodottoRequestDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price can't be null or empty.");
        }
        if (prodottoRequestDTO.getQuantity() <= 0) {
            throw new InsufficientQuantityException("Product quantity can't be null or empty.");
        }
        if (prodottoRequestDTO.getCertification() == null || prodottoRequestDTO.getCertification().isBlank()) {
            throw new IllegalArgumentException("Product certification can't be null or empty.");
        }
        if (prodottoRequestDTO.getExpirationDate() == null) {
            throw new IllegalArgumentException("Product expiration date can't be null or empty.");
        }


    }
}