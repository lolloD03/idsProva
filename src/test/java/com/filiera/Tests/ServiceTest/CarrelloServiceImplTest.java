package com.filiera.Tests.ServiceTest;

import com.filiera.model.products.Carrello;
import com.filiera.model.products.Prodotto;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.services.CarrelloServiceImpl;
import com.filiera.services.ProductService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CarrelloServiceImplTest {

    private CarrelloServiceImpl carrelloService;
    private ProductService productService;
    private InMemoryProductRepository productRepository;
    private Prodotto prodotto;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productRepository = mock(InMemoryProductRepository.class);
        carrelloService = new CarrelloServiceImpl(productRepository, productService);

        prodotto = new Prodotto(
                "Pane",
                "Pane fresco",
                2.0,
                1,
                null,
                5
        );
    }

    @Test
    void addProduct_shouldAddValidProduct() {
        when(productService.getById(prodotto.getId())).thenReturn(Optional.of(prodotto));

        List<Prodotto> result = carrelloService.addProduct(prodotto);
        assertTrue(result.contains(prodotto));
    }

    @Test
    void addProduct_shouldThrowIfProductNotFound() {
        when(productService.getById(prodotto.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                carrelloService.addProduct(prodotto)
        );

        assertEquals("Il prodotto con id " + prodotto.getId() + " non esiste.", exception.getMessage());
    }

    @Test
    void removeProduct_shouldRemoveExistingProduct() {
        when(productService.getById(prodotto.getId())).thenReturn(Optional.of(prodotto));
        carrelloService.addProduct(prodotto);

        List<Prodotto> result = carrelloService.removeProduct(prodotto);
        assertFalse(result.contains(prodotto));
    }

    @Test
    void getInvoice_shouldReturnFormattedInvoice() {
        when(productService.getById(prodotto.getId())).thenReturn(Optional.of(prodotto));
        carrelloService.addProduct(prodotto);
        Carrello carrello = carrelloService.getCarrello();

        String invoice = carrelloService.getInvoice(carrello).toString();

        assertTrue(invoice.contains("Prodotto: Pane"));
        assertTrue(invoice.contains("Prezzo: 2.0"));
    }

    @Test
    void clearCarrello_shouldClearWhenNotEmpty() {
        when(productService.getById(prodotto.getId())).thenReturn(Optional.of(prodotto));
        carrelloService.addProduct(prodotto);

        assertDoesNotThrow(() -> carrelloService.clearCarrello());
        assertTrue(carrelloService.getCarrello().getProducts().isEmpty());
    }

    @Test
    void clearCarrello_shouldThrowWhenEmpty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                carrelloService.clearCarrello()
        );

        assertEquals("Il carrello è già vuoto.", exception.getMessage());
    }


    @Test
    void getCarrello_shouldReturnInstance() {
        Carrello c = carrelloService.getCarrello();
        assertNotNull(c);
    }

}