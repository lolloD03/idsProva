package com.filiera.controller;

import com.filiera.model.users.Acquirente;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryUserRepository;
import com.filiera.services.AcquirenteServiceImpl;

import java.util.Optional;
import java.util.UUID;

public class AcquirenteController {

    private final AcquirenteServiceImpl service;
    private final InMemoryUserRepository userRepo;

    public AcquirenteController(AcquirenteServiceImpl service, InMemoryUserRepository userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    public Acquirente createAcquirente(Acquirente buyer) {
        try {
            userRepo.save(buyer);
            return buyer;
        } catch (Exception e) {
            System.out.println("Error creating acquirente: " + e.getMessage());
            return null;
        }
    }

   public Acquirente updateAcquirente(String email , String name, String password , UUID id) {

           try {
               Optional<User> optional = userRepo.findById(id);

               Acquirente actualBuyer = getAcquirenteById(id);

               actualBuyer.setEmail(email);
               actualBuyer.setName(name);
               actualBuyer.setPassword(password);
               userRepo.save(actualBuyer);
               return actualBuyer;
           }
           catch(Exception e) {
               System.out.println("Error updating acquirente: " + e.getMessage());
               return null;
           }

   }

   public Acquirente getAcquirenteById(UUID id) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent() && optional.get() instanceof Acquirente) {
            return (Acquirente) optional.get();
        } else {
            System.out.println("Acquirente con id " + id + " non trovato.");
            return null;
        }
    }

    public void deleteAcquirente(UUID id) {
        try {
            if(userRepo.findById(id).isPresent()) {
                userRepo.deleteById(id);
            } else {
                System.out.println("Acquirente con id " + id + " non trovato.");
            }
        }
        catch(Exception e) {
            System.out.println("Error deleting acquirente: " + e.getMessage());
        }
    }


    public Optional<User> findById(UUID id) {
          return userRepo.findById(id);
    }


    public AcquirenteController(AcquirenteServiceImpl service) {
        this.service = service;
    }
}
