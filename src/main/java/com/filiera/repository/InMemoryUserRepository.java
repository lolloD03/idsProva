package com.filiera.repository;

import com.filiera.model.users.User;

import java.util.*;

public class InMemoryUserRepository implements CrudRepository<User, UUID> {
    private final Map<UUID, User> store = new HashMap<>();
    @Override public User save(User u) { store.put(u.getId(), u); return u; }
    @Override public Optional<User> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    @Override public List<User> findAll() { return new ArrayList<>(store.values()); }
    @Override public void deleteById(UUID id) { store.remove(id); }
    public User findByUsername(String username) {
        //return store.values().stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
        return null;
    }
}
