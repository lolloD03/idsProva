package com.filiera.model.sellers;

public class Produttore extends Venditore {

    private String process;

    public Produttore() {
        super();
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

    /*
    Public void createProduct(Prodotto prodotto) {}
     */

    
}
