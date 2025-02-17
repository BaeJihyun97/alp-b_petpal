package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.PetSitterService;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.petpal.petpalapp.domain.PetSitter;
@Repository
public interface PetSitterServiceRepository extends JpaRepository<PetSitterService, Long> {
    List<PetSitterService> findByPetSitter(PetSitter petSitter);
    List<PetSitterService> findByPetSitter_PetSitterId(Long petSitterId);
    
    // 필요한 경우 추가 쿼리 메서드
    List<PetSitterService> findByPetTypeCodeId(String petTypeCodeId);
    List<PetSitterService> findByLocationCodeId(String locationCodeId);
    List<PetSitterService> findByPetSizeCodeId(String petSizeCodeId);
} 