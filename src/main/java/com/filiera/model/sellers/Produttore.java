package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;
import java.util.UUID;

public class Produttore extends Venditore {

    private String process;
    private String certificati;

    public Produttore() {
    }



    public Produttore(UUID id , String password, String email, String name, String surname, String cultivationProcess , String certificati ) {
        super(id, password, email, name, surname);
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
