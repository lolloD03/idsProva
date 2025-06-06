package com.filiera.model.administration;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;


import java.util.List;
import java.util.UUID;

public class Curatore  extends User {

    private List<Prodotto> productsToApprove;

    public Curatore() {super();}

    public Curatore(String password, String email, String name, RuoloUser ruoloUser) {
        super( password, email, name, ruoloUser);
    }

    // Additional methods specific to Curatore can be added here
}
