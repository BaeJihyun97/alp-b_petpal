package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.Pet;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUser_UserId(Long userId);
    List<Pet> findByPetOwnerId(Long petOwnerId);
} 