package com.filiera.model;

public class Curatore extends User {

    public Curatore() {
        super();
    }

    public Curatore(String password, String email, String name, String surname) {
        super(password, email, name, surname);
    }

    @Override
    public String getRole() {
        return "Curatore";
    }

    // Additional methods specific to Curatore can be added here
}
