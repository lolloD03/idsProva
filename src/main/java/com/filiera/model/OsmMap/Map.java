package com.filiera.model.OsmMap;

import java.util.List;

public class Map {

    private List<Indirizzo> map;

    public Map() {
    }

    public Map(List<Indirizzo> map) {
        this.map = map;
    }

    public List<Indirizzo> getMap() {
        return map;
    }

    public void setMap(List<Indirizzo> map) {
        this.map = map;
    }

    public void addIndirizzo(Indirizzo indirizzo) {
        map.add(indirizzo);
    }
    public void removeIndirizzo(Indirizzo indirizzo) {
        map.remove(indirizzo);
    }

    public void updateIndirizzo(Indirizzo indirizzo) {
        int index = map.indexOf(indirizzo);
        if (index >= 0) {
            map.set(index, indirizzo);
        }
    }

}
