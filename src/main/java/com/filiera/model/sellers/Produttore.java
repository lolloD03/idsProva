package com.filiera.model.sellers;

import com.filiera.model.Products.Prodotto;

import java.util.Date;
import java.util.UUID;

public class Produttore extends Venditore {

    private String process;
    private String certificati;

    public Produttore() {
    }



    public Produttore(UUID id , String password, String email,int partitaIva, String name, String cultivationProcess, String certificati) {
        super(id, password, email, name ,partitaIva);
        this.process = cultivationProcess;
        this.certificati = certificati;
    }




    public String getCultivationProcess() {
        return process;
    }
    public void setCultivationProcess(String cultivationProcess) {
        this.process = cultivationProcess;
    }

    public String getCertificati() {return certificati;}
    public void setCertificati(String certificati) {}





    
}
