package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.users.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Venditore extends User {
    //TODO ho modificato da private a protected
    protected List<Prodotto> prodotti;


    public Venditore() {
        super();
        prodotti = new ArrayList<>();
    }

    public Venditore(String name , String address ) {
        prodotti = new ArrayList<>();
    }
    //TODO: Modifica da verificare ->
    protected void addProductToInventory(Prodotto prodotto){prodotti.add(prodotto);}
    protected void removeProductFromInventory(Prodotto prodotto) {prodotti.remove(prodotto);}
}
