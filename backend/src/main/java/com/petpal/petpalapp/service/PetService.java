package com.petpal.petpalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.petpal.petpalapp.domain.Pet;
import com.petpal.petpalapp.repository.PetRepository;
import com.petpal.petpalapp.mapper.PetMapper;
import com.petpal.petpalapp.dto.request.PetRequestDTO;
import com.petpal.petpalapp.dto.response.PetResponseDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public List<PetResponseDTO> getAllPets() {
        return petRepository.findAll().stream()
            .map(petMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public PetResponseDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pet not found"));
        return petMapper.toResponseDTO(pet);
    }

    public List<PetResponseDTO> getPetsByUserId(Long userId) {
        return petRepository.findByUser_UserId(userId).stream()
            .map(petMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public PetResponseDTO createPet(PetRequestDTO request) {
        if (request.getPetOwnerId() == null) {
            throw new IllegalArgumentException("반려동물 주인 ID는 필수입니다.");
        }
        
        Pet pet = petMapper.toEntity(request);
        return petMapper.toResponseDTO(petRepository.save(pet));
    }

    @Transactional
    public PetResponseDTO updatePet(Long id, PetRequestDTO request) {
        if (request.getPetOwnerId() == null) {
            throw new IllegalArgumentException("반려동물 주인 ID는 필수입니다.");
        }

        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pet not found"));
        
        Pet updatedPet = petMapper.toEntity(request);
        pet.setPetName(updatedPet.getPetName());
        pet.setPetAge(updatedPet.getPetAge());
        pet.setPetOwnerId(updatedPet.getPetOwnerId());
        pet.setPetTypeCodeId(updatedPet.getPetTypeCodeId());
        pet.setPetTypeCodeGroupId(updatedPet.getPetTypeCodeGroupId());
        pet.setPetSizeCodeId(updatedPet.getPetSizeCodeId());
        pet.setPetSizeCodeGroupId(updatedPet.getPetSizeCodeGroupId());
        
        return petMapper.toResponseDTO(petRepository.save(pet));
    }

    public List<PetResponseDTO> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByPetOwnerId(ownerId).stream()
            .map(petMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}