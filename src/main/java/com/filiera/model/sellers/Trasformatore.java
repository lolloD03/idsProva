package com.filiera.model.sellers;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("TRASFORMATORE")
public  class Trasformatore extends Venditore {

    private String process;

    public Trasformatore() {
        super();
    }



    public Trasformatore(String password, String email, String name, RuoloUser ruoloUser, int partitaIva ) {
        super( password, email, name, ruoloUser ,partitaIva);
        this.process = process;
    }

    public String getProductionProcess() {
        return process;
    }

    public void setProductionProcess(String productionProcess) {
        this.process = productionProcess;
    }




    }




