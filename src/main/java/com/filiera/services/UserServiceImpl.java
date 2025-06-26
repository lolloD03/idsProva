package com.filiera.services;

import com.filiera.model.sellers.Produttore;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
public class    UserServiceImpl implements UserService {
    private final InMemoryUserRepository userRepo;

    @Autowired
    public UserServiceImpl(InMemoryUserRepository repo) { this.userRepo = repo; }
    @Override
    public User register(User user) {

        if (userRepo.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("Email già registrata!");
        }
        return userRepo.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }


    @Override
    public Produttore registerProduttore(Produttore produttore) {
        if (produttore == null) {
            throw new IllegalArgumentException("Produttore cannot be null");
        }
        if (userRepo.findAll().stream().anyMatch(u -> u.getEmail().equals(produttore.getEmail()))) {
            throw new IllegalArgumentException("Email già registrata!");
        }


        return userRepo.save(produttore);
    }

    @Override
    public User findById(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Utente non trovato con id: " + id)));
    }



    public void deleteById(UUID id){ userRepo.deleteById(id); }

}
