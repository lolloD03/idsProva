package com.filiera.services;

import com.filiera.model.dto.ProdottoRequestDTO;
import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface ProductService {
    //Prodotto createProduct(UUID seller, String name, String descrizione, double price, int quantity, String certitfication);
    Prodotto createProduct(ProdottoRequestDTO prodottoRequestDTO);
    List<Prodotto> listAll();
    Optional<Prodotto> getById(UUID id);
    Prodotto updateProduct(UUID prodottoId, String name, String descrizione, double price, int quantity);
    void deleteProduct(UUID prodottoId);

    public List<Prodotto> getApprovedProducts();


    void riduciQuantità(UUID prodottoId, int quantity);
}
