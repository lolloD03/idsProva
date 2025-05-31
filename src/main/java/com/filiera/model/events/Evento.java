package com.filiera.model.events;

import com.filiera.model.OsmMap.Indirizzo;

import java.util.Date;

public class Evento {

    private Indirizzo indirizzo;
    private String name;
    private String description;
    private int maxPeople;
    private Date date;
    private AnimatoreFiliera animatore;

    public Evento(){}

    public Evento (Indirizzo indirizzo, String name, String description, int maxPeople, Date date, AnimatoreFiliera animatore) {
        this.indirizzo = indirizzo;
        this.name = name;
        this.description = description;
        this.maxPeople = maxPeople;
        this.date = date;
        this.animatore = animatore;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AnimatoreFiliera getAnimatore() {
        return animatore;
    }

    public void setAnimatore(AnimatoreFiliera animatore) {
        this.animatore = animatore;
    }

}

