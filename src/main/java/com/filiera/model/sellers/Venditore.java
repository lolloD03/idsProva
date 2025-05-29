package com.filiera.model.sellers;

import com.filiera.model.Prodotto;
import com.filiera.model.users.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Venditore extends User {
    private List<Prodotto> prodotti;


    public Venditore() {
        super();
        prodotti = new ArrayList<>();
    }

    public Venditore(String password, String email, String name, String surname) {
        super(password, email, name, surname);
        prodotti = new ArrayList<>();
    }
}
