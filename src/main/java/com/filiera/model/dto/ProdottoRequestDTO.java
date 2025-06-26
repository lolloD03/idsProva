package com.filiera.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data // Include @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor // Costruttore senza argomenti
@AllArgsConstructor // Costruttore con tutti gli argomenti
@Builder // Per creare facilmente istanze (utile nei test)
public class ProdottoRequestDTO {

    @NotBlank(message = "Il nome del prodotto non può essere vuoto")
    private String name;

    @NotBlank(message = "La descrizione del prodotto non può essere vuota")
    private String description;

    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
    private double price;

    @Min(value = 1, message = "La quantità deve essere maggiore di 0")
    private int quantity;

    @NotBlank(message = "La certificazione non può essere vuota")
    private String certification;

    @NotNull(message = " La data di scadenza non può essere nulla")
    private LocalDate expirationDate;
}