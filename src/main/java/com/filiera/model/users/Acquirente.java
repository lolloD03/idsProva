package com.filiera.model.users;

import java.util.UUID;

public class Acquirente extends User {

    public Acquirente() {
        super();
    }

    public Acquirente(UUID id , String password, String email, String name, String surname) {
        super(id, password, email, name, surname);
    }



    public String toString() {
        return "Acquirente{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    // Additional methods specific to Acquirente can be added here
}
