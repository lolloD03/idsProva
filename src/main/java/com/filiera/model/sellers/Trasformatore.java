package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Trasformatore extends Venditore {

    private String process;
    private String certification;

    public Trasformatore() {
        super();
    }



    public Trasformatore(UUID id , String password, String email, String name, String surname , String process , String certification ) {
        super(id, password, email, name, surname );
        this.process = process;
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




