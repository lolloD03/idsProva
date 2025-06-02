package com.filiera.model.Products;

import com.filiera.model.users.Acquirente;

import java.util.List;

public class Carrello {

    private int id;

    private List<Prodotto> products;

    private Acquirente buyer;

    private double totalPrice;

    public Carrello() {}

    public Carrello(int id, List<Prodotto> products , Acquirente buyer) {
        this.id = id;
        this.products = products;
        this.buyer = buyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Prodotto> getProducts() {
        return products;
    }

    public void setProducts(List<Prodotto> products) {
        this.products = products;
    }

    public Acquirente getBuyer() {
        return buyer;
    }

    public void setBuyer(Acquirente buyer) {
        this.buyer = buyer;
    }

    public double getTotalPrice() {
        for (Prodotto product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

}
