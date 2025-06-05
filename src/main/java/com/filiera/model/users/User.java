package com.filiera.model.users;
import java.util.UUID;
public abstract class User {

    private UUID id;
    private String password;
    private String email;
    private String name;
    private String surname;


    public User(String password, String email, String name) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public User() {}



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


}
