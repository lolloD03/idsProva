package com.filiera.services;
import java.util.*;


import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class VenditoreServiceImpl {

   private final UserService userService;

    public VenditoreServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public void updateVenditoreByID(UUID id, String email, String name,int partitaIva){

        Venditore v = getVenditoreByID(id);
        v.setEmail(email);
        v.setName(name);
        v.setPartitaIva(partitaIva);

    }

    public Venditore getVenditoreByID(UUID id){
       if(!(userService.findById(id) instanceof Venditore)){
           throw new IllegalArgumentException("Non è un venditore");
       }
       return (Venditore) userService.findById(id);
    }

}
