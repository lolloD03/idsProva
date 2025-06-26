package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;


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

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrdine> items = new ArrayList<>();

    //private double totale;

    private LocalDate dataOrdine;


    public double getTotalPrice() {
        if (items.isEmpty()) {
            throw new RuntimeException("L'ordine è vuoto");
        }
        return items.stream().mapToDouble(ItemOrdine::getTotal).sum();
    }


    public void addItem(ItemOrdine item) {
        this.items.add(item);
        item.setOrdine(this);
    }

    public void removeItem(ItemOrdine item) {
        this.items.remove(item);
        item.setOrdine(null);
    }
}