package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ItemCarrello {

    @ManyToOne
    private Prodotto product;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idItem;

    private int quantity;

    public ItemCarrello(Prodotto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseQuantity(int q) {
        this.quantity += q;
    }

    public void decreaseQuantity(int q) {
        this.quantity -= q;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

}
