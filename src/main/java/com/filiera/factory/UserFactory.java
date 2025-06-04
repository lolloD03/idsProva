package com.filiera.factory;

import com.filiera.model.administration.Curatore;
import com.filiera.model.sellers.Produttore;
import com.filiera.model.sellers.Trasformatore;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;

import java.util.UUID;

public class UserFactory {

    public static User createUser(String name, String address, RuoloUser role) {

        UUID userId = UUID.randomUUID(); // Generate a unique ID for the user

        switch (role) {
            case PRODUTTORE:
                return new Produttore(name, address, "Default Cultivation Process");
            case TRASFORMATORE:
                return new Trasformatore(name, address, "Default Transformation Process");
            case CURATORE:
                return new Curatore(name, address);
            default:
                throw new IllegalArgumentException("Unknown user role: " + role);
        }
    }

}
