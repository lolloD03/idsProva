package com.filiera.services;
import java.util.*;


import com.filiera.exception.SellerNotFoundException;
import com.filiera.model.sellers.Venditore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VenditoreServiceImpl {

   private final UserService userService;

   @Autowired
    public VenditoreServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public void updateSellerByID(UUID id, String email, String name, String partitaIva){

        Venditore v = getSellerByID(id);
        v.setEmail(email);
        v.setName(name);
        v.setPartitaIva(partitaIva);

    }

    public Venditore getSellerByID(UUID id){
       if(!(userService.findById(id) instanceof Venditore)){
           throw new SellerNotFoundException("Seller not found with ID: " + id);
       }
       return (Venditore) userService.findById(id);
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
