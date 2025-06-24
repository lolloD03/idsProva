package com.filiera.services;

import com.filiera.exception.InsufficientQuantityException;
import com.filiera.exception.ProductNotFoundException;
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
    public Prodotto createProduct(ProdottoRequestDTO prodottoRequestDTO, UUID venditorId) {
        logger.info("Creating new product from request DTO for vendor: {}", venditorId);

        // Validate input parameters
        validateProductInput(prodottoRequestDTO);

        // Find seller
        Venditore venditore = vendRepo.findById(venditorId)
                .orElseThrow(() -> new ProductNotFoundException("Venditore non trovato con id: " + venditorId));

        // Create and save product
        Prodotto prodotto = Prodotto.builder()
                .name(prodottoRequestDTO.getName())
                .description(prodottoRequestDTO.getDescrizione())
                .price(prodottoRequestDTO.getPrice())
                .availableQuantity(prodottoRequestDTO.getQuantity())
                .certification(prodottoRequestDTO.getCertification())
                .expirationDate(prodottoRequestDTO.getExpirationDate()) // Se presente
                .seller(venditore)
                .build();

        Prodotto savedProduct = prodRepo.save(prodotto);
        venditore.addProdotto(savedProduct);

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
    public Prodotto updateProduct(UUID productId, ProdottoRequestDTO prodottoRequestDTO, UUID venditorId) {
        logger.info("Aggiornamento del prodotto con id: {} per il venditore: {}", productId, venditorId);

        // 1. Valida i parametri di input per l'aggiornamento
        validateProductInput(prodottoRequestDTO);

        // 2. Trova il prodotto esistente
        Prodotto existingProduct = prodRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Prodotto non trovato con id: " + productId));

        // 3. Verifica la proprietà del venditore
        // È fondamentale assicurarsi che solo il venditore proprietario possa modificare il prodotto.
        if (!existingProduct.getSeller().getId().equals(venditorId)) {
            logger.warn("Tentativo non autorizzato di aggiornare il prodotto {} da parte del venditore {}. Il prodotto appartiene al venditore {}.",
                    productId, venditorId, existingProduct.getSeller().getId());
            throw new UnauthorizedOperationException("Il venditore con ID " + venditorId +
                    " non è autorizzato a modificare il prodotto con ID " + productId);
        }

        // 4. Aggiorna i campi del prodotto
        existingProduct.setName(prodottoRequestDTO.getName());
        existingProduct.setDescription(prodottoRequestDTO.getDescrizione());
        existingProduct.setPrice(prodottoRequestDTO.getPrice());
        existingProduct.setAvailableQuantity(prodottoRequestDTO.getQuantity());
        existingProduct.setCertification(prodottoRequestDTO.getCertification());
        existingProduct.setExpirationDate(prodottoRequestDTO.getExpirationDate());

        // 5. Salva il prodotto aggiornato
        Prodotto updatedProduct = prodRepo.save(existingProduct);
        logger.info("Prodotto aggiornato con successo con id: {}", updatedProduct.getId());
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
    public void riduciQuantita(UUID prodottoId, int quantity) {
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

    public Prodotto checkProductState(UUID prodottoId) {

        Prodotto prodotto = prodRepo.findById(prodottoId)
                .orElseThrow(() -> new ProductNotFoundException("Il prodotto con ID " + prodottoId + " non esiste."));

        if(prodotto.getState()==StatoProdotto.IN_ATTESA_DI_APPROVAZIONE)
            throw new RuntimeException("Non puoi acquistare un prodotto in attesa di approvazione");

        if(prodotto.getState()==StatoProdotto.ESAURITO)
            throw new RuntimeException("Non puoi acquistare un prodotto esaurito");

        if(prodotto.getState()==StatoProdotto.RIFIUTATO)
            throw new RuntimeException("Non puoi acquistare un prodotto rifiutato");

        return prodotto;
    }

    private void validateProductInput(ProdottoRequestDTO prodottoRequestDTO) {
        if (prodottoRequestDTO == null) {
            throw new IllegalArgumentException("Il prodotto non può essere nullo.");
        }

        if (prodottoRequestDTO.getName() == null || prodottoRequestDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto.");
        }
        if (prodottoRequestDTO.getDescrizione() == null || prodottoRequestDTO.getDescrizione().isBlank()) {
            throw new IllegalArgumentException("La descrizione del prodotto non può essere vuota.");
        }
        if (prodottoRequestDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore di 0.");
        }
        if (prodottoRequestDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("La quantità deve essere maggiore di 0.");
        }
        if (prodottoRequestDTO.getCertification() == null || prodottoRequestDTO.getCertification().isBlank()) {
            throw new IllegalArgumentException("La certificazione non può essere vuota.");
        }
        if (prodottoRequestDTO.getExpirationDate() == null) {
            throw new IllegalArgumentException("La data di scadenza non può essere nulla.");
        }


    }
}