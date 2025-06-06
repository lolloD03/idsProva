package com.filiera.model.products;

import com.filiera.model.sellers.Venditore;

public class ProdottoProduttore extends Prodotto {


    private String cultivationProcess;

    public ProdottoProduttore() {
        super();
    }

    public ProdottoProduttore(String name, String description, double price, int quantity, Venditore seller, int daysToExpire, String cultivationProcess) {
        super(name, description, price, quantity, seller, daysToExpire);
        this.cultivationProcess = cultivationProcess;
    }

    public String getCultivationProcess() {
        return cultivationProcess;
    }

    public void setCultivationProcess(String cultivationProcess) {
        this.cultivationProcess = cultivationProcess;
    }



}
