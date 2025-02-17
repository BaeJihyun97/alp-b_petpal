package com.petpal.petpalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.petpal.petpalapp.domain.PetSitter;
import com.petpal.petpalapp.repository.PetSitterRepository;
import com.petpal.petpalapp.mapper.PetSitterMapper;
import com.petpal.petpalapp.dto.request.PetSitterRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterResponseDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import com.petpal.petpalapp.common.enums.PState;
import com.petpal.petpalapp.repository.PUserRepository;
import com.petpal.petpalapp.service.PetsitterAvailabilityService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetSitterService {
    private final PetSitterRepository petSitterRepository;
    private final PetSitterMapper petSitterMapper;
    private final PUserRepository pUserRepository;
    private final PetsitterAvailabilityService petsitterAvailabilityService;
    
    public List<PetSitterResponseDTO> getAllPetSitters() {
        return petSitterRepository.findAll().stream()
            .map(petSitterMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public PetSitterResponseDTO getPetSitterById(Long id) {
        PetSitter petSitter = petSitterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PetSitter not found"));
        return petSitterMapper.toResponseDTO(petSitter);
    }

    public PetSitterResponseDTO getPetSitterByUserId(Long userId) {
        PetSitter petSitter = petSitterRepository.findByUser_UserId(userId);
        if (petSitter == null) {
            throw new RuntimeException("PetSitter not found for user");
        }
        return petSitterMapper.toResponseDTO(petSitter);
    }

    @Transactional
    public PetSitterResponseDTO createPetSitter(PetSitterRequestDTO request) {
        if (petSitterRepository.findByUser_UserId(request.getUserId()) != null) {
            throw new RuntimeException("이미 등록된 펫시터입니다.");
        }

        PetSitter petSitter = petSitterMapper.toEntity(request);

        petSitter.setUser(pUserRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found")));

        petSitter.setState(PState.ENABLED);
        petSitter.setCreatedAt(LocalDateTime.now());
        petSitter.setUpdatedAt(LocalDateTime.now());

        PetSitter savedPetSitter = petSitterRepository.save(petSitter);
        
        // 가능 시간 초기화 추가
        petsitterAvailabilityService.initializeAvailability(savedPetSitter.getPetSitterId());
        System.out.println("가능 시간 초기화 완료");
        
        return petSitterMapper.toResponseDTO(savedPetSitter);
    }

    @Transactional
    public PetSitterResponseDTO updatePetSitter(Long id, PetSitterRequestDTO request) {
        PetSitter petSitter = petSitterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("PetSitter not found"));
        
        PetSitter updatedPetSitter = petSitterMapper.toEntity(request);
        petSitter.setIntroduction(updatedPetSitter.getIntroduction());
        petSitter.setLocationCodeId(updatedPetSitter.getLocationCodeId());
        petSitter.setLocationCodeGroupId(updatedPetSitter.getLocationCodeGroupId());
        petSitter.setNickname(updatedPetSitter.getNickname());
        petSitter.setPhoneNumber(updatedPetSitter.getPhoneNumber());
        petSitter.setUpdatedAt(LocalDateTime.now());
        return petSitterMapper.toResponseDTO(petSitterRepository.save(petSitter));
    }

    @Transactional
    public void deletePetSitter(Long id) {
        petSitterRepository.deleteById(id);
    }
} 