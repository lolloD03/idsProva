package com.filiera.model.events;

import com.filiera.model.OsmMap.Indirizzo;
import lombok.Data;

import java.util.Date;

@Data
public class Evento {

    private Indirizzo address;
    private String name;
    private String description;
    private int maxPeople;
    private Date date;
    private AnimatoreFiliera animator;

    public Evento(){}

    public Evento (Indirizzo address, String name, String description, int maxPeople, Date date, AnimatoreFiliera animator) {
        this.address = address;
        this.name = name;
        this.description = description;
        this.maxPeople = maxPeople;
        this.date = date;
        this.animator = animator;
    }



}

