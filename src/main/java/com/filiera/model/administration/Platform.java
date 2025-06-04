package com.filiera.model.administration;

import com.filiera.model.products.Prodotto;

import java.util.ArrayList;
import java.util.List;

public class Platform {

    private List<Prodotto> productsInPlatform;

    public Platform() {
        productsInPlatform = new ArrayList<>();
    }

    public List<Prodotto> getProductsInPlatform(Prodotto product) {
        return productsInPlatform;
    }

    public void setProductsInPlatform(List<Prodotto> productsInPlatform) {
        this.productsInPlatform = productsInPlatform;
    }

}
