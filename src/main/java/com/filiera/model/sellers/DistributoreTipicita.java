package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.List;

public class DistributoreTipicita extends Venditore {

    private List<Prodotto> productsBundle;
    private List<List<Prodotto>> listOfBundles;

    public DistributoreTipicita() {
        super();
    }

    @Override
    public String getRole() {
        return "DISTRIBUTOREDITIPICITA";
    }

    public DistributoreTipicita(String name , String address) {
        super(name , address);
    }


    public void addToBundle(Prodotto prodotto) {
    productsBundle.add(prodotto);
    }

    public void removeFromBundle(Prodotto prodotto) {productsBundle.remove(prodotto);}

    public void addToListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.add(bundleOfproducts);}

    public void removeFromListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.remove(bundleOfproducts);}

}
