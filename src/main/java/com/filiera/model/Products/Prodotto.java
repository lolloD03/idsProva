package com.filiera.model.Products;

import com.filiera.model.sellers.Venditore;

public class Prodotto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean approved;
    private Venditore seller;

    public Prodotto() {}

    public Prodotto(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }


}
