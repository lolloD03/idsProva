package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;

public class Produttore extends Venditore {

    private String process;

    public Produttore() {
        super();
    }

    @Override
    public String getRole() {
        return "";
    }

    public Produttore(String name , String address , String cultivationProcess) {
        super(name , address);
        this.process = cultivationProcess;
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
