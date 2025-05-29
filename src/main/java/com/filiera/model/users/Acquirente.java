package com.filiera.model.users;

public class Acquirente extends User {

    public Acquirente() {
        super();
    }

    public Acquirente(String password, String email, String name, String surname) {
        super(password, email, name, surname);
    }

    @Override
    public String getRole() {
        return "Acquirente";
    }

    // Additional methods specific to Acquirente can be added here
}
