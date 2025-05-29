package com.filiera.model;

import java.util.List;

public class Trasformatore extends Venditore {

    public Trasformatore() {
        super();
    }

    public Trasformatore(String password, String email, String name, String surname) {
        super(password, email, name, surname);
    }

    @Override
    public String getRole() {
        return "Trasformatore";
    }



    // Additional methods specific to Trasformatore can be added here
}
