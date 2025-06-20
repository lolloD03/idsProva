package com.filiera.model.users;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "app_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_user", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String password;
    private String email;
    private String name;
    private RuoloUser ruolo;


    public User(String password, String email, String name , RuoloUser ruolo) {
        //this.id = UUID.randomUUID();
        this.password = password;
        this.email = email;
        this.name = name;
        this.ruolo = ruolo;
    }

    public User() {
        //this.id = UUID.randomUUID();
    }



    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {this.id = id;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public RuoloUser getRuolo() {return ruolo;}
    public void setRuolo(RuoloUser ruolo) {this.ruolo = ruolo;}


}
