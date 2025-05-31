package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.List;

public class DistributoreTipicita extends Venditore {

    private List<Prodotto> productsBundle;

    public DistributoreTipicita() {
        super();
    }

    public DistributoreTipicita(String name , String address) {
        super(name , address);
    }


    public void addToBundle(Prodotto prodotto) {
    productsBundle.add(prodotto);
    }




}
