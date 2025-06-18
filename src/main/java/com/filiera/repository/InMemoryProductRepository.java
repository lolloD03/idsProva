package com.filiera.repository;

import com.filiera.model.products.Prodotto;
import com.filiera.model.products.StatoProdotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface InMemoryProductRepository extends JpaRepository<Prodotto, UUID> { }
