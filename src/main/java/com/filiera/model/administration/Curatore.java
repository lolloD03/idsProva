package com.filiera.model.administration;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;
import jakarta.persistence.Entity;


@Entity
public class Curatore  extends User {

    public Curatore() {super();}

    public Curatore(String password, String email, String name, RuoloUser ruoloUser) {
        super( password, email, name, ruoloUser);
    }

}
