package com.filiera.model.products;

import com.filiera.model.sellers.Venditore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PRODOTTODISTRIBUTORE")
public class ProdottoDistributore extends Prodotto{

    @OneToMany
    private List<Prodotto> prodotti;

    public ProdottoDistributore() {
        this.prodotti = new ArrayList<>();
    }

    public ProdottoDistributore(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public void addToProdotti(Prodotto prodotto) {
        prodotti.add(prodotto);
    }

    public void removeFromProdotti(Prodotto prodotto) {
        prodotti.remove(prodotto);
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

}


