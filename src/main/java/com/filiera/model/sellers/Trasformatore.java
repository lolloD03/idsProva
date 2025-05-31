package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;

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


    }




