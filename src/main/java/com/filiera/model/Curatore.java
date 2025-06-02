package com.filiera.model;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.Products.StatoProdotto;
import com.filiera.model.users.User;


import java.util.List;

public class Curatore  extends User {

    private List<Prodotto> productsToApprove;

    private String name;

    public Curatore() {
        super();
    }

    @Override
    public String getRole() {
        return "";
    }


    public void approveProduct(Prodotto prodotto) {
        prodotto.setState(StatoProdotto.APPROVATO);
        productsToApprove.remove(prodotto);
    }

    public void rejectProduct(Prodotto prodotto) {
        prodotto.setState(StatoProdotto.REJECTED);
        productsToApprove.remove(prodotto);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    // Additional methods specific to Curatore can be added here
}
