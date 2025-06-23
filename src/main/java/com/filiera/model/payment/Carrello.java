package com.filiera.model.payment;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.Acquirente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IdGeneratorType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ElementCollection
    private List<ItemCarrello> products;

    @OneToOne
    private Acquirente buyer;

    private double totalPrice;

    public Carrello() {

        this.products = new ArrayList<>();
        this.buyer = new Acquirente();
    }

    public Carrello(List<ItemCarrello> products , Acquirente buyer) {

        this.products = products;
        this.buyer = buyer;
    }


    public double getTotalPrice() {

        if(products.isEmpty()) {
            throw new RuntimeException("Il carrello è vuoto");
        }

        return products.stream().mapToDouble(ItemCarrello::getTotal).sum();

    }

    public void addProduct(Prodotto product , int quantity) {
        if(product==null){throw new RuntimeException("Il prodotto è nullo");}

        for (ItemCarrello item : products) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.increaseQuantity(quantity);
                return;
            }
        }
        products.add(new ItemCarrello(product, quantity));
    }

    public void removeProduct(Prodotto product , int quantity) {

        Iterator<ItemCarrello> iter = products.iterator();

        while (iter.hasNext()) {
            ItemCarrello item = iter.next();
            if (item.getProduct().getId().equals(product.getId())) {
                item.decreaseQuantity(quantity);
                if (item.getQuantity() <= 0) {
                    iter.remove();
                }
                return;
            }
        }

    }

    public void clearCarrello() {
        products.clear();
    }


}
