package com.filiera.services;
import java.util.*;


import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;


public class VenditoreServiceImpl {

    public InMemoryProductRepository productRepository;
    public InMemoryUserRepository userRepository;
    public ProductServiceImpl productService;

    public VenditoreServiceImpl(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /* public Venditore getVenditoreByID(UUID id){
        Optional<User> user = userRepository.findById(id);
         if (user.isEmpty()) {
             throw new RuntimeException("Utente non trovato con ID: " + id);
         }
         User u = user.get();

         if (!(u instanceof Venditore)) {
             throw new IllegalArgumentException("L'utente con ID " + id + " non è un venditore.");
         }
        return (Venditore) u;
    }

    public void removeVenditoreByID(UUID id) {
        Venditore v = getVenditoreByID(id);
        userRepository.deleteById(id);
    }

    public void UpdateVenditoreByID(UUID id, String email, String name, String surname){

        User v = getVenditoreByID(id);

        if (email != null) {
            v.setEmail(email);
        }
        if (name != null) {
            v.setName(name);
        }
        if (surname != null) {
            v.setSurname(surname);
        }
        userRepository.save(v);
    }
    public void UpdatePasswordByID(UUID id, String password){

        Venditore v = getVenditoreByID(id);

        if(password == v.getPassword()) {
            v.setPassword(password);
            userRepository.save(v);
        } else {
            System.out.println("Password errata");
        }
    }*/
}
