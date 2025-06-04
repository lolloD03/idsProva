package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.users.User;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Venditore extends User {

    protected List<Prodotto> prodotti;


    public Venditore() {
        super();
        prodotti = new ArrayList<>();
    }

    public Venditore(UUID id , String password, String email, String name, String surname) {
        super(id, password, email, name, surname);
        prodotti = new ArrayList<>();
    }

    protected void addProductToInventory(Prodotto prodotto){prodotti.add(prodotto);}
    protected void removeProductFromInventory(Prodotto prodotto) {prodotti.remove(prodotto);}
}
