package com.filiera.services;

import com.filiera.model.dto.ProdottoRequestDTO;
import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Venditore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface ProductService {
    //Prodotto createProduct(UUID seller, String name, String descrizione, double price, int quantity, String certitfication);
    List<Prodotto> listAll();
    Optional<Prodotto> getById(UUID id);

    Prodotto createProduct(ProdottoRequestDTO prodottoRequestDTO, UUID venditoreId);

    Prodotto updateProduct(UUID prodottoId, ProdottoRequestDTO prodottoRequestDTO , UUID venditoreId);

    void deleteProduct(UUID prodottoId);

    public List<Prodotto> getApprovedProducts();


    void riduciQuantita(UUID prodottoId, int quantity);

    Prodotto checkProductState(UUID prod);
}
