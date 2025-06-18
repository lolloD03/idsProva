package com.filiera.model.sellers;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;

import java.util.List;
import java.util.UUID;

public class DistributoreTipicita extends Venditore {

    private List<List<Prodotto>> listOfBundles;

    public DistributoreTipicita() {
        super();
    }


    public DistributoreTipicita(String password, String email, String name, RuoloUser ruoloUser, int partitaIva) {
        super( password, email, name, ruoloUser , partitaIva);
    }


    public void addToListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.add(bundleOfproducts);}

    public void removeFromListOfBundles(List<Prodotto> bundleOfproducts) {listOfBundles.remove(bundleOfproducts);}

}
