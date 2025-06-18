package com.filiera.repository;

import com.filiera.model.events.AnimatoreFiliera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InMemoryAnimatoreRepository extends JpaRepository<AnimatoreFiliera , UUID> { }
