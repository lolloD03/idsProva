package com.filiera.model;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.users.User;


import java.util.List;
import java.util.UUID;

public class Curatore  extends User {

    private List<Prodotto> productsToApprove;

    public Curatore(UUID id ,String password, String email, String name, String surname) {
        super( id, password, email, name, surname);
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    // Additional methods specific to Curatore can be added here
}
