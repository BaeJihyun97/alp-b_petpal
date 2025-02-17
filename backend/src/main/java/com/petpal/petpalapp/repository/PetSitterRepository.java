package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.PetSitter;

public interface PetSitterRepository extends JpaRepository<PetSitter, Long> {
    PetSitter findByUser_UserId(Long userId);
} 