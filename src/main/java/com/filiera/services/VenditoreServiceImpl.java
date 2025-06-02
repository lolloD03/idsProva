package com.filiera.services;

import com.filiera.model.Products.Prodotto;
import com.filiera.model.sellers.Venditore;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;

public class VenditoreServiceImpl {

    public InMemoryProductRepository productRepository;
    public InMemoryUserRepository userRepository;

    public VenditoreServiceImpl(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Prodotto createProduct(Venditore seller, String name, String descrizione, double price, int quantity) {

        if(userRepository.findById(seller.getId()).isEmpty()){
            throw new IllegalArgumentException("Il venditore con ID " + seller.getId() + " non esiste.");
        }

        Prodotto product = new Prodotto(name, descrizione, price, quantity, seller);
        return productRepository.save(product);

    }


    public Prodotto updateProduct(Prodotto updatedProduct) {

        Prodotto actualProduct = productRepository.findById(updatedProduct.getId())
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + updatedProduct.getId()));

        actualProduct.setName(updatedProduct.getName());
        actualProduct.setDescription(updatedProduct.getDescription());
        actualProduct.setPrice(updatedProduct.getPrice());
        actualProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());

        return productRepository.save(actualProduct);

    }

    public void deleteProduct(Prodotto prodotto) {

        if (productRepository.findById(prodotto.getId()).isEmpty()) {
            throw new RuntimeException("Il prodotto con ID " + prodotto.getId() + " non esiste.");
        }

        productRepository.deleteById(prodotto.getId());
    }



}
