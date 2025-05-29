package com.filiera.model;

import com.filiera.model.users.User;

public class Curatore extends User {

    public Curatore() {
        super();
    }

    public Curatore(String password, String email, String name, String surname) {
        super(password, email, name, surname);
    }

    public void approveProduct(Prodotto prodotto) {

        prodotto.setStato(StatoProdotto.APPROVATO);

    }

    @Override
    public String getRole() {
        return "Curatore";
    }

    // Additional methods specific to Curatore can be added here
}
