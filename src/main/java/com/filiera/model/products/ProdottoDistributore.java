package com.filiera.model.products;

import com.filiera.model.sellers.Venditore;

import java.util.ArrayList;
import java.util.List;

public class ProdottoDistributore extends Prodotto {

    private List<Prodotto> prodotti;

    public ProdottoDistributore() {
        super();
    }

    public ProdottoDistributore(String name, String description, double price, int quantity, Venditore seller, int daysToExpire) {
        super(name, description, price, quantity,seller, daysToExpire);
        this.prodotti = new ArrayList<>();
    }

    public void addToProdotti(Prodotto prodotto) {
        prodotti.add(prodotto);
    }

    public void removeFromProdotti(Prodotto prodotto) {
        prodotti.remove(prodotto);
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

}
