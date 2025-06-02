package com.filiera.model.Products;

import com.filiera.model.Curatore;
import com.filiera.model.sellers.Venditore;

import java.time.LocalDate;
import java.util.UUID;

public class Prodotto {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int availableQuantity;
    private StatoProdotto state;
    private Venditore seller;
    private Curatore approvedBy;
    private LocalDate expirationDate;

    public Prodotto() {
        // Default constructor
        this.id = UUID.randomUUID();
    }

    public Prodotto(String name, String description, double price, int quantity, Venditore seller,int daysToExpire) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = quantity;
        this.seller =seller;
        this.setExpirationDate(LocalDate.now().plusDays(daysToExpire));
    }


    public void approveBy(Curatore curatore) {
        this.state = StatoProdotto.APPROVATO;
        this.approvedBy = curatore;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public StatoProdotto getState() {
        return state;
    }

    public void setState(StatoProdotto state) {
        this.state = state;
    }

    public Venditore getSeller() {
        return seller;
    }

    public void setSeller(Venditore seller) {
        this.seller = seller;
    }

    public Curatore getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Curatore approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

}
