package com.filiera.model.administration;

import com.filiera.model.products.Prodotto;
import com.filiera.model.users.RuoloUser;
import com.filiera.model.users.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


// Dato che Curatore non ha campi specifici propri oltre a quelli ereditati,
// non ha bisogno di @Getter e @Setter diretti.
// Li eredita già dalla classe User.
@Entity
@DiscriminatorValue("CURATORE") // Valore specifico per il discriminatore
@NoArgsConstructor // Genera il costruttore senza argomenti, necessario per JPA
@SuperBuilder // Essenziale per estendere il builder dalla classe padre User
@ToString(callSuper = true) // Genera un toString che include i campi della classe padre User
public class Curatore extends User {



    // Questa classe non ha campi specifici, eredita tutto da User.
    // Con @SuperBuilder e @NoArgsConstructor, i costruttori manuali non sono più necessari.
}