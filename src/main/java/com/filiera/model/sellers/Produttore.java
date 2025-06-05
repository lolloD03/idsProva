package com.filiera.model.sellers;


import com.filiera.model.users.RuoloUser;

import java.util.UUID;

public class Produttore extends Venditore {

    private String process;
    private String certificati;

    public Produttore() {
    }



    public Produttore(String password, String email, RuoloUser ruoloUser, int partitaIva, String name, String cultivationProcess, String certificati) {
        super(password, email, name, ruoloUser ,partitaIva);
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
