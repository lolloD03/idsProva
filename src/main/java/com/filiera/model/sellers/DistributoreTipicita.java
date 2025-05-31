package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.List;

public class DistributoreTipicita extends Venditore {

    private List<Prodotto> productsBundle;
    private List<List<Prodotto>> listOfBundles;  //TODO: modifica effettuata in questa riga da verificre;

    public DistributoreTipicita() {
        super();
    }

    @Override
    public String getRole() {
        return "";
    }

    public DistributoreTipicita(String name , String address) {
        super(name , address);
    }


    public void addToBundle(Prodotto prodotto) {
    productsBundle.add(prodotto);
    }
    //TODO Modifiche da verificare
    public void removeFromBundle(Prodotto prodotto) {productsBundle.remove(prodotto);}
    public void addToListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.add(bundleOfproducts);}
    public void removeFromListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.remove(bundleOfproducts);}
    //TODO fine modifche da verificare
}
