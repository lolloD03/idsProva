package com.filiera.repository;

import com.filiera.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface InMemoryUserRepository extends JpaRepository<User, UUID> { }
