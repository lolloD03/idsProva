package com.filiera.model.sellers;

import com.filiera.model.OsmMap.Indirizzo;
import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public abstract class Venditore extends User {


    @OneToMany(mappedBy = "seller")
    private List<Prodotto> prodotti;

    @Embedded
    private Indirizzo indirizzo;

    private int partitaIva;

    public Venditore() {
        super();
        prodotti = new ArrayList<>();
    }

    public Venditore(String password, String email, String name, RuoloUser ruolo, int partitaIva) {
        super( password, email, name , ruolo);
        this.partitaIva = partitaIva;
        prodotti = new ArrayList<>();
    }

    public void setProdotti(List<Prodotto> prodotti) {this.prodotti = prodotti;}
    public List<Prodotto> getProdotti() {return prodotti;}
    public int getPartitaIva() {return partitaIva;}
    public void setPartitaIva(int nuovaPartitaIva) {partitaIva = nuovaPartitaIva;}
    protected void prod(Prodotto prodotto){prodotti.add(prodotto);}
    protected void removeProductFromInventory(Prodotto prodotto) {prodotti.remove(prodotto);}

}
