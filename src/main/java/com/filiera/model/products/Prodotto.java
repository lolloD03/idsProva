package com.filiera.model.products;

import com.filiera.model.administration.Curatore;
import com.filiera.model.sellers.Venditore;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;
@Entity
// @DiscriminatorColumn non è necessario su una classe che non è parte di una gerarchia di ereditarietà JPA.
// Se Prodotto fosse classe base per altri tipi di prodotti (es. ProdottoAgricolo, ProdottoArtigianale),
// allora avrebbe senso. Ma in base al tuo schema, Prodotto è una classe concreta.
@Data // Genera getter, setter, equals, hashCode, toString
@NoArgsConstructor // Costruttore senza argomenti per JPA
// Abilita il pattern Builder per una creazione oggetti fluida
@EqualsAndHashCode(of = "id") // Genera equals/hashCode solo basato sull'ID per le entità
@ToString // Genera toString
@SuperBuilder
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <--- Cambiato da UUID a Long, generazione automatica
    private UUID id; // <--- Tipo Long

    @Enumerated(EnumType.STRING) // Mappa l'enum come stringa nel DB
    private StatoProdotto state; // Enum StatoProdotto (APPROVATO, ESAURITO, IN_ATTESA_DI_APPROVAZIONE, RIFIUTATO)

    @ManyToOne // Molti prodotti possono essere venduti da un singolo venditore
    @JoinColumn(name = "seller_id", nullable = false) // Colonna FK nella tabella 'prodotto', obbligatoria
    private Venditore seller;

    @ManyToOne // Molti prodotti possono essere approvati da un singolo curatore
    @JoinColumn(name = "approved_by_id") // Colonna FK, può essere null se non ancora approvato
    private Curatore approvedBy;

    // LocalDate non richiede @Temporal(TemporalType.DATE) con JPA moderno (Spring Boot 2.x+ / Hibernate 5+),
    // dato che viene mappato correttamente a DATE per default. Puoi lasciarlo per chiarezza se preferisci.
    private LocalDate expirationDate;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT") // Utile per testi più lunghi
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int availableQuantity;

    private String certification; // Potrebbe essere nullable = true per default

    // --- Lifecycle Callbacks JPA ---
    // Questo metodo viene eseguito prima che l'entità venga salvata per la prima volta
    @PrePersist
    public void prePersist() {
        if (this.state == null) {
            this.state = StatoProdotto.IN_ATTESA_DI_APPROVAZIONE;
        }
        // Se non viene settata una data di scadenza, puoi settarla qui
        // if (this.expirationDate == null) {
        //     this.expirationDate = LocalDate.now().plusDays(30); // Esempio: 30 giorni di default
        // }
    }

    // --- Metodi con Logica di Business ---
    // Nota: I getter e setter standard sono forniti da @Data


    public void approveBy(Curatore curatore) {
        this.setState(StatoProdotto.APPROVATO);
        this.setApprovedBy(curatore);
    }

    public void rejectBy(Curatore curatoreObj) {
        this.setState(StatoProdotto.RIFIUTATO);
        this.setApprovedBy(curatoreObj);
    }
}