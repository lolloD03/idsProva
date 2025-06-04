package com.filiera.model.sellers;

import com.filiera.model.products.Prodotto;

import java.util.UUID;

public class Produttore extends Venditore {

    private String process;
    private String certificati;

    public Produttore() {
    }



    public Produttore(UUID id , String password, String email, String name, String nomeNegozio ,String surname, String cultivationProcess  ) {
        super(id, password, email, name , nomeNegozio);
        this.process = cultivationProcess;
        this.certificati = certificati;
    }


    public String getCultivationProcess() {
        return process;
    }

    public void setCultivationProcess(String cultivationProcess) {
        this.process = cultivationProcess;
    }

    public void addProductToInventory(Prodotto prodotto) {
    prodotti.add(prodotto);
    }
    public void removeProductFromInventory(Prodotto prodotto) {prodotti.remove(prodotto);}


    
}
