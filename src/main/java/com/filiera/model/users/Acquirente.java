package com.filiera.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("ACQUIRENTE")
public class Acquirente extends User {


    private String address;


    public Acquirente() {
        super();
    }

    public Acquirente( String password, String email, String name , RuoloUser ruoloUser, String surname) {
        super( password, email, name , ruoloUser);
    }



    public String toString() {
        return "Acquirente{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    // Additional methods specific to Acquirente can be added here
}
