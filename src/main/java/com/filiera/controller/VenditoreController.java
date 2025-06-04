package com.filiera.controller;

import com.filiera.model.sellers.Venditore;
import com.filiera.services.UserService;
import com.filiera.services.UserServiceImpl;
import com.filiera.services.VenditoreServiceImpl;

import java.util.UUID;

public class VenditoreController {
    private final UserService userService;
    private final VenditoreServiceImpl venditoreService;

    public VenditoreController(UserService userService, VenditoreServiceImpl venditoreService) {
        this.userService = userService;
        this.venditoreService = venditoreService;
    }

    public Venditore getVenditoreByID(UUID id){
        if(id == null){
            throw new IllegalArgumentException("Invalid parameters");
        }
       return venditoreService.getVenditoreByID(id);

    }
    public void updateVenditoreByID(UUID id, String email, String name, int partitaIva){
        if(id==null || email == null || name == null || partitaIva == 0){
            throw new IllegalArgumentException("Invalid parameters");
        }
        venditoreService.updateVenditoreByID(id, email, name, partitaIva);
    }
    // TODO definire chi come e perche con quale autorita si permette di eliminare un utente (puo eliminare solo se stesso in teoria)
    public void removeVenditoreByID(UUID id){
        if(id == null){
            throw new IllegalArgumentException("Invalid parameters");
        }
        userService.deleteById(id);
    }

}
