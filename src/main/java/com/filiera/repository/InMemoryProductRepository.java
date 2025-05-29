package com.filiera.repository;

import com.filiera.model.Prodotto;

import java.util.*;

public class InMemoryProductRepository implements CrudRepository<Prodotto, UUID> {
    private final Map<UUID, Prodotto> store = new HashMap<>();
    @Override public Prodotto save(Prodotto p) { store.put(p.getId(), p); return p; }
    @Override public Optional<Prodotto> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<Prodotto> findAll() { return new ArrayList<>(store.values()); }
    @Override public void deleteById(UUID id) { store.remove(id); }
}
