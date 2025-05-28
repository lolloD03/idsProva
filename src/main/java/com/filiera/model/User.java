package com.filiera.model;

public abstract class User {

    private String id;
    private String password;
    private String email;
    private String name;
    private String surname;


    public User(String id, String password, String email, String name, String surname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public abstract String getRole();

    public String getId() {
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
