package com.filiera.Tests.RepoTest;

import com.filiera.model.products.Prodotto;
import com.filiera.model.products.StatoProdotto;
import com.filiera.repository.InMemoryProductRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;
    private Prodotto prodotto;

    @BeforeEach
    void setUp() {
        repository = new  InMemoryProductRepository();
        prodotto = new Prodotto(
                "Miele",
                "Miele biologico",
                5.0,
                10,
                null,
                10,
                "DOP"
        );
    }

    @Test
    void save_shouldStoreProduct() {
        repository.save(prodotto);
        Optional<Prodotto> found = repository.findById(prodotto.getId());

        assertTrue(found.isPresent());
        assertEquals("Miele", found.get().getName());
    }

    @Test
    void findById_shouldReturnCorrectProduct() {
        repository.save(prodotto);
        Optional<Prodotto> result = repository.findById(prodotto.getId());

        assertTrue(result.isPresent());
        assertEquals(prodotto, result.get());
    }

    @Test
    void findById_shouldReturnEmptyIfNotFound() {
        Optional<Prodotto> result = repository.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllSavedProducts() {
        Prodotto p1 = new Prodotto( "P1", "desc", 1.0, 5, null, 5,"DOP");
        Prodotto p2 = new Prodotto( "P2", "desc", 2.0, 3, null, 3,"DOP");

        repository.save(p1);
        repository.save(p2);

        List<Prodotto> all = repository.findAll();

        assertEquals(2, all.size());
        assertTrue(all.contains(p1));
        assertTrue(all.contains(p2));
    }

    @Test
    void deleteById_shouldRemoveProduct() {
        repository.save(prodotto);
        repository.deleteById(prodotto.getId());

        assertTrue(repository.findById(prodotto.getId()).isEmpty());
    }

    @Test
    void findByState_shouldReturnMatchingProducts() {
        Prodotto p1 = new Prodotto( "P1", "desc", 1.0, 5, null, 5,"DOP");
        Prodotto p2 = new Prodotto( "P2", "desc", 2.0, 3, null, 3,"DOP");
        Prodotto p3 = new Prodotto( "P3", "desc", 2.0, 3, null, 3,"DOP");


        p1.setState(StatoProdotto.APPROVATO);
        p2.setState(StatoProdotto.RIFIUTATO);
        p3.setState(StatoProdotto.APPROVATO);


        repository.save(p1);
        repository.save(p2);
        repository.save(p3);

        List<Prodotto> approvati = repository.findByState(StatoProdotto.APPROVATO);

        assertEquals(2, approvati.size());
        assertEquals(p1, approvati.get(0));
    }

    @Test
    void getApprovedProducts_shouldReturnOnlyApproved() {
        Prodotto p1 = new Prodotto( "P1", "desc", 1.0, 5, null, 5,"DOP");
        Prodotto p2 = new Prodotto( "P2", "desc", 2.0, 3, null, 3,"DOP");

        p1.setState(StatoProdotto.APPROVATO);
        p2.setState(StatoProdotto.RIFIUTATO);

        repository.save(p1);
        repository.save(p2);

        List<Prodotto> approved = repository.getApprovedProducts();

        assertEquals(1, approved.size());
        assertEquals(p1, approved.get(0));
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByState() {
    }

    @Test
    void getApprovedProducts() {
    }
}