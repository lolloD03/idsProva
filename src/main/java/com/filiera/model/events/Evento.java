package com.filiera.model.events;

import com.filiera.model.OsmMap.Indirizzo;

import java.util.Date;

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

    public Indirizzo getAddress() {
        return address;
    }

    public void setAddress(Indirizzo address) {
        this.address = address;
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

    public AnimatoreFiliera getAnimator() {
        return animator;
    }

    public void setAnimatore(AnimatoreFiliera animatore) {
        this.animator = animatore;
    }

}

