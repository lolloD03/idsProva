package com.filiera.model.administration;

import com.filiera.model.Prodotto;
import com.filiera.model.users.User;


import java.util.List;
import java.util.UUID;

public class Curatore  extends User {

    private List<Prodotto> productsToApprove;

    public Curatore() {super();}

    public Curatore(UUID id ,String password, String email, String name) {
        super( id, password, email, name);
    }

    // Additional methods specific to Curatore can be added here
}
