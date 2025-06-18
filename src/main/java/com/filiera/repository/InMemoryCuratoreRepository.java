package com.filiera.repository;

import com.filiera.model.administration.Curatore;
import com.filiera.model.events.AnimatoreFiliera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InMemoryCuratoreRepository extends JpaRepository<Curatore, UUID> { }
