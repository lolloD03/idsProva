package com.filiera.model.sellers;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.filiera.model.OsmMap.Indirizzo;
import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
// Non è necessaria @DiscriminatorValue qui se Venditore è l'unica sottoclasse
// della strategia SINGLE_TABLE o se vuoi usare il nome della classe di default.
// Se hai altre sottoclassi di User e vuoi un valore specifico per Venditore, aggiungila:
// @DiscriminatorValue("VENDITORE")
@Getter // Genera i getter per i campi di Venditore
@Setter // Genera i setter per i campi di Venditore
@NoArgsConstructor // Genera il costruttore senza argomenti, necessario per JPA
@SuperBuilder // Permette di usare il pattern Builder e funziona con l'ereditarietà
@ToString(callSuper = true) // Genera un toString che include anche i campi della classe padre
public abstract class Venditore extends User { // Nota: Manteniamo abstract se ci sono sottoclassi concrete di Venditore

    @JsonManagedReference
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    // Inizializza la lista per evitare NullPointerException quando JPA la carica o se la usi prima del salvataggio
    // Se la lista è null quando JPA la carica, ci penserà JPA a inizializzarla con una sua implementazione.
    // L'inizializzazione nell'istanza @SuperBuilder è gestita meglio.
    private List<Prodotto> prodotti = new ArrayList<>();

    @Embedded
    @Column(nullable = false) // Assumiamo che l'indirizzo non debba essere null
    private Indirizzo indirizzo; // Assicurati di avere la classe Indirizzo con @Embeddable

    @Column(nullable = false, unique = true) // La partita IVA non può essere null e deve essere unica
    private String partitaIva;

    // Con @SuperBuilder, @NoArgsConstructor e @Getter/@Setter,
    // i costruttori e i metodi toString manuali non sono più necessari.
    // L'ID è ora gestito dalla classe User tramite @GeneratedValue.

    // I metodi per aggiungere/rimuovere prodotti dovrebbero essere pubblici per l'interazione esterna
    // e potrebbero voler ritornare 'this' per chaining o 'boolean' per indicare successo.
    public void addProdotto(Prodotto prodotto) {
        if (this.prodotti == null) { // Aggiunto un controllo di sicurezza
            this.prodotti = new ArrayList<>();
        }
        this.prodotti.add(prodotto);
        prodotto.setSeller(this); // Importante per la relazione bidirezionale @OneToMany
    }

    public void removeProdotto(Prodotto prodotto) {
        if (this.prodotti != null) { // Aggiunto un controllo di sicurezza
            this.prodotti.remove(prodotto);
            prodotto.setSeller(null); // Importante per la relazione bidirezionale
        }
    }
}