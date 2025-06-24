package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@NoArgsConstructor
@Entity
@Data
public class Ordine {




    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID numeroOrdine; // UUID o codice ordine

    @ManyToOne
    @JoinColumn(name = "buyer_id" , nullable = false)
    private Acquirente buyer;

    @OneToMany
    @JoinColumn(name = "items" , nullable = false)
    private List<ItemCarrello> items;

    private double totale;

    private LocalDate dataOrdine;



}
