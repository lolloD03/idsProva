package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;

import java.util.Date;
import java.util.List;

public class Ordine {

    public Ordine(){}

    public Ordine(String numeroOrdine, String usernameCliente, List<Prodotto> items, double totale, Date dataOrdine) {
        this.numeroOrdine = numeroOrdine;
        this.usernameCliente = usernameCliente;
        this.items = items;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }


    private String numeroOrdine; // UUID o codice ordine
    private String usernameCliente;
    private List<Prodotto> items;
    private double totale;
    private Date dataOrdine;

    public String getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(String numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }

    public List<Prodotto> getItems() {
        return items;
    }

    public void setItems(List<Prodotto> items) {
        this.items = items;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

}
