package com.filiera.model.products;

import com.filiera.model.administration.Curatore;
import com.filiera.model.sellers.Venditore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@DiscriminatorColumn(name = "tipo_prodotto", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private StatoProdotto state;

    @ManyToOne
    private Venditore seller;

    @ManyToOne
    private Curatore approvedBy;

    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    private String name;
    private String description;
    private double price;
    private int availableQuantity;
    private String certification;

    public Prodotto() {

    }

    public Prodotto(String name, String description, double price, int quantity, Venditore seller,int daysToExpire,String certification) {
        this.certification = certification;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = quantity;
        this.seller =seller;
        this.setExpirationDate(LocalDate.now().plusDays(daysToExpire));
    }

    public static Prodotto creaProdotto(String name, String description, double price, int quantity, Venditore seller,String certification) {
        Prodotto prodotto = new Prodotto();
        prodotto.certification = certification;
        prodotto.id = UUID.randomUUID();
        prodotto.name = name;
        prodotto.description = description;
        prodotto.price = price;
        prodotto.availableQuantity = quantity;
        prodotto.seller = seller;
        prodotto.state = StatoProdotto.IN_ATTESA_DI_APPROVAZIONE;
        return prodotto;
    }

    public void aggiornaProdotto(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = quantity;
    }

    public void approveBy(Curatore curatore) {
        this.state = StatoProdotto.APPROVATO;
        this.approvedBy = curatore;
    }

    public void rejectBy(Curatore curatoreObj) {
        this.state = StatoProdotto.RIFIUTATO;
        this.approvedBy = curatoreObj;
    }

    public String toString(){

        return "Prodotto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                ", state=" + state +
                ", seller=" + seller +
                ", approvedBy=" + approvedBy +
                ", expirationDate=" + expirationDate +
                ", certification='" + certification + '\'' +
                '}';
    }
}
