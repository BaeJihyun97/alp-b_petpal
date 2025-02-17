package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petpal.petpalapp.domain.PetsitterAvailability;

import java.util.Optional;

@Repository
public interface PetsitterAvailabilityRepository extends JpaRepository<PetsitterAvailability, Long> {
    Optional<PetsitterAvailability> findByPetsitterId(Long petsitterId);
} 