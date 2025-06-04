package com.filiera.model.sellers;

import com.filiera.model.Prodotto;

import java.util.UUID;

public  class Trasformatore extends Venditore {

    private String process;
    private String certification;

    public Trasformatore() {
        super();
    }



    public Trasformatore(UUID id , String password, String email, String name,String nomeNegozio , String process , String certification ) {
        super(id, password, email, name , nomeNegozio);
        this.process = process;
        this.certification = certification;
    }

    public String getProductionProcess() {
        return process;
    }

    public void setProductionProcess(String productionProcess) {
        this.process = productionProcess;
    }


    protected void addProductToInventory(Prodotto prodotto) {
        prodotti.add(prodotto);
    }
    protected void removeProductFromInventory(Prodotto prodotto) {prodotti.remove(prodotto);}

    }




