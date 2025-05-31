package com.filiera.model.sellers;

public class Trasformatore extends Venditore {

    private String process;

    public Trasformatore() {
        super();
    }

    public Trasformatore(String name , String address , String productionProcess) {
        super(name, address);
        this.process = productionProcess;
    }

    public String getProductionProcess() {
        return process;
    }

    public void setProductionProcess(String productionProcess) {
        this.process = productionProcess;
    }

    /*
    Public void createProduct(Prodotto prodotto) {}
     */



}
