package com.filiera.model.sellers;


import com.filiera.model.users.RuoloUser;

import java.util.UUID;

public class Produttore extends Venditore {

    private String process;

    public Produttore() {
    }



    public Produttore(String password, String email, RuoloUser ruoloUser, int partitaIva, String name) {
        super(password, email, name, ruoloUser ,partitaIva);
    }




    public String getCultivationProcess() {
        return process;
    }
    public void setCultivationProcess(String cultivationProcess) {this.process = cultivationProcess;}






    
}
