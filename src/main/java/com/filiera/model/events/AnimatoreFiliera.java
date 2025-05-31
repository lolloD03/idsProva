package com.filiera.model.events;

import java.util.List;

public class AnimatoreFiliera {

    private long id;
    private String name;
    private List<Evento> eventsCreated;

    public AnimatoreFiliera() {
    }

    public AnimatoreFiliera(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Evento> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(List<Evento> eventsCreated) {
        this.eventsCreated = eventsCreated;
    }
}
