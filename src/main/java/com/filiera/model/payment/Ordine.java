package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Data
public class Ordine {

    public Ordine(){}

    public Ordine(UUID buyerId, List<ItemCarrello> items, double totale, Date dataOrdine) {
        this.numeroOrdine = randomUUID();
        this.buyerId = buyerId;
        this.items = items;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }



    private UUID numeroOrdine; // UUID o codice ordine
    private UUID buyerId;
    private List<ItemCarrello> items;
    private double totale;
    private Date dataOrdine;



}
