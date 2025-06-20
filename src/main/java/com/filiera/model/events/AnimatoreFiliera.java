package com.filiera.model.events;

import com.filiera.model.users.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("ANIMATORE")
public class AnimatoreFiliera extends User {
/*
    private String name;

    private List<Evento> eventsCreated;

    public AnimatoreFiliera() {
    }

    public AnimatoreFiliera(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Evento> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(List<Evento> eventsCreated) {
        this.eventsCreated = eventsCreated;
    }


 */

}
