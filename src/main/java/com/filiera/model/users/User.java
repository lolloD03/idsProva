package com.filiera.model.users;
import java.util.UUID;
public abstract class User {

    private UUID id;
    private String password;
    private String email;
    private String name;
    private String surname;


    public User(String password, String email, String name, String surname) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public User() {
        this.id = UUID.randomUUID();
    }

    public abstract String getRole();

    public UUID getId() {
        return id;
    }

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
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

}
