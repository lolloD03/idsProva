package com.filiera.model;

public class Produttore extends Venditore {


    public Produttore() {
        super();
    }

    public Produttore(String password, String email, String name, String surname) {
        super(password, email, name, surname);
    }


    @Override
    public String getRole() {
        return "";
    }
    // Additional methods specific to Produttore can be added here

}
