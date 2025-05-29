package com.filiera.controller;

import com.filiera.model.User;
import com.filiera.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserService service;
    public UserController(UserService service) { this.service = service; }




    public List<User> list() { return service.listAll(); }
}
