package com.filiera.model.products;

import com.filiera.model.sellers.Venditore;

public class ProdottoTrasformatore extends Prodotto {

    String transformationProcess;

    public ProdottoTrasformatore() {
        super();
    }

    public ProdottoTrasformatore(String name, String description, double price, int quantity, Venditore seller, int daysToExpire, String transformationProcess) {
        super(name, description, price, quantity, seller, daysToExpire);
        this.transformationProcess = transformationProcess;
    }

    public String getTransformationProcess() {
        return transformationProcess;
    }

    public void setTransformationProcess(){
        this.transformationProcess = transformationProcess;
    }



}
