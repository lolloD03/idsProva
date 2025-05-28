package com.filiera.model;

public abstract class Venditore extends User {


    public Venditore(String id, String password, String email, String name, String surname) {
        super(id, password, email, name, surname);
    }
}
