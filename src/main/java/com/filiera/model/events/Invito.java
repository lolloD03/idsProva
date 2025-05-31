package com.filiera.model.events;

import com.filiera.model.sellers.Venditore;

public class Invito {

    private int id;

    private AnimatoreFiliera sender;

    private Venditore receiver;

    private String description;

    public Invito(){}

    public Invito(int id, AnimatoreFiliera sender, Venditore receiver, String description) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnimatoreFiliera getSender() {
        return sender;
    }

    public void setSender(AnimatoreFiliera sender) {
        this.sender = sender;
    }

    public Venditore getReceiver() {
        return receiver;
    }

    public void setReceiver(Venditore receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
