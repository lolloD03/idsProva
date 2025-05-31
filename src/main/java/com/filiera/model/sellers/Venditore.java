package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.ArrayList;
import java.util.List;

public abstract class Venditore  {
    private List<Prodotto> prodotti;


    public Venditore() {
        super();
        prodotti = new ArrayList<>();
    }

    public Venditore(String name , String address ) {
        prodotti = new ArrayList<>();
    }
}
