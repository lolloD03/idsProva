package com.filiera.model;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.users.User;


import java.util.List;
import java.util.UUID;

public class Curatore  extends User {

    private List<Prodotto> productsToApprove;

    private String name;

    public Curatore(UUID uuid, String nome, String email, String password) {
        super();
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    // Additional methods specific to Curatore can be added here
}
