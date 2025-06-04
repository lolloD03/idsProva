package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public  class Trasformatore extends Venditore {

    private String process;
    private String certification;

    public Trasformatore() {
        super();
    }



    public Trasformatore(UUID id , String password, String email, String name,int partitaIva, String process , String certification ) {
        super(id, password, email, name, partitaIva);
        this.process = process;
        this.certification = certification;
    }

    public String getProductionProcess() {
        return process;
    }

    public void setProductionProcess(String productionProcess) {
        this.process = productionProcess;
    }




    }




