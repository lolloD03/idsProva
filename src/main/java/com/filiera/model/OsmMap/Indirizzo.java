package com.filiera.model.OsmMap;

public class Indirizzo {
    private String citta;

    private String via;

    private String numeroCivico;

    public Indirizzo() {}

    public Indirizzo(String citta, String via, String numeroCivico) {
        this.citta = citta;
        this.via = via;
        this.numeroCivico = numeroCivico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico){
        this.numeroCivico = numeroCivico;
    }

}
