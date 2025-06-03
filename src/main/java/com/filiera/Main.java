package com.filiera;

import com.filiera.controller.ProductController;
import com.filiera.model.sellers.Produttore;
import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.User;
import com.filiera.repository.CrudRepository;
import com.filiera.repository.InMemoryProductRepository;
import com.filiera.repository.InMemoryUserRepository;
import com.filiera.services.ProductService;
import com.filiera.services.ProductServiceImpl;
import com.filiera.services.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // Example usage of the Filiera class
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        ProductService productService = new ProductServiceImpl(productRepository, userService, userRepository);
        ProductController productController = new ProductController(productService);

        Venditore venditore = new Produttore();
        venditore.setName("Mario Rossi");
        userRepository.save(venditore);
        productController.createProduct(venditore, "Pomodoro", "Pomodoro fresco", 1.5, 100);
        productController.createProduct(venditore, "Carota", "Carota dolce", 0.8, 200);

        // List all products
        System.out.println("Elenco prodotti:");
        for (var prodotto : productController.list()) {
            System.out.println("Nome: " + prodotto.getName() + ", Descrizione: " + prodotto.getDescription() +
                    ", Prezzo: " + prodotto.getPrice() + ", Quantità disponibile: " + prodotto.getAvailableQuantity());
        }


    }
}
