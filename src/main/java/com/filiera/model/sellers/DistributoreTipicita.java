package com.filiera.model.sellers;

import com.filiera.model.Prodotto;

import java.util.List;
import java.util.UUID;

public class DistributoreTipicita extends Venditore {

    private List<Prodotto> productsBundle;
    private List<List<Prodotto>> listOfBundles;

    public DistributoreTipicita() {
        super();
    }


    public DistributoreTipicita(UUID id , String password, String email, String name, String nomeNegozio) {
        super(id, password, email, name, nomeNegozio);
    }


    public void addToBundle(Prodotto prodotto) {
    productsBundle.add(prodotto);
    }

    public void removeFromBundle(Prodotto prodotto) {productsBundle.remove(prodotto);}

    public void addToListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.add(bundleOfproducts);}

    public void removeFromListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.remove(bundleOfproducts);}

}
