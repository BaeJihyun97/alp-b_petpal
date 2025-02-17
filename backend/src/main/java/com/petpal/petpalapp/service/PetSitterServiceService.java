package com.petpal.petpalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.petpal.petpalapp.domain.PetSitterService;
import com.petpal.petpalapp.repository.PetSitterServiceRepository;
import com.petpal.petpalapp.mapper.PetSitterServiceMapper;
import com.petpal.petpalapp.dto.request.PetSitterServiceRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import com.petpal.petpalapp.domain.PetSitter;
import jakarta.persistence.EntityNotFoundException;
import com.petpal.petpalapp.repository.PetSitterRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PetSitterServiceService {
    private final PetSitterServiceRepository petSitterServiceRepository;
    private final PetSitterRepository petSitterRepository;
    private final PetSitterServiceMapper petSitterServiceMapper;
    public PetSitterServiceResponseDTO save(PetSitterServiceRequestDTO requestDTO) {
        PetSitter petSitter = petSitterRepository.findById(requestDTO.getPetSitterId())
            .orElseThrow(() -> new EntityNotFoundException("펫시터를 찾을 수 없습니다."));

        PetSitterService petSitterService = new PetSitterService();
        petSitterService.setPetSitter(petSitter);
        petSitterService.setPetTypeCodeId(requestDTO.getPetTypeCodeId());
        petSitterService.setPetTypeCodeGroupId(requestDTO.getPetTypeCodeGroupId());
        petSitterService.setServiceFee(requestDTO.getServiceFee());
        petSitterService.setLocationCodeId(requestDTO.getLocationCodeId());
        petSitterService.setLocationCodeGroupId(requestDTO.getLocationCodeGroupId());
        petSitterService.setPetSizeCodeId(requestDTO.getPetSizeCodeId());
        petSitterService.setPetSizeCodeGroupId(requestDTO.getPetSizeCodeGroupId());

        PetSitterService savedService = petSitterServiceRepository.save(petSitterService);
        return petSitterServiceMapper.toResponseDTO(savedService);
    }

    public PetSitterServiceResponseDTO findById(Long serviceId) {
        PetSitterService service = petSitterServiceRepository.findById(serviceId)
            .orElseThrow(() -> new EntityNotFoundException("서비스를 찾을 수 없습니다."));
        return petSitterServiceMapper.toResponseDTO(service);
    }

    public List<PetSitterServiceResponseDTO> findByPetSitterId(Long petSitterId) {
        return petSitterServiceRepository.findByPetSitter_PetSitterId(petSitterId).stream()
            .map(petSitterServiceMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public PetSitterServiceResponseDTO update(Long serviceId, PetSitterServiceRequestDTO requestDTO) {
        PetSitterService service = petSitterServiceRepository.findById(serviceId)
            .orElseThrow(() -> new EntityNotFoundException("서비스를 찾을 수 없습니다."));

        service.setPetTypeCodeId(requestDTO.getPetTypeCodeId());
        service.setPetTypeCodeGroupId(requestDTO.getPetTypeCodeGroupId());
        service.setServiceFee(requestDTO.getServiceFee());
        service.setLocationCodeId(requestDTO.getLocationCodeId());
        service.setLocationCodeGroupId(requestDTO.getLocationCodeGroupId());
        service.setPetSizeCodeId(requestDTO.getPetSizeCodeId());
        service.setPetSizeCodeGroupId(requestDTO.getPetSizeCodeGroupId());

        PetSitterService updatedService = petSitterServiceRepository.save(service);
        return petSitterServiceMapper.toResponseDTO(updatedService);
    }

    // private PetSitterServiceResponseDTO convertToResponseDTO(PetSitterService service) {
    //     PetSitterServiceResponseDTO responseDTO = new PetSitterServiceResponseDTO();
    //     responseDTO.setServiceId(service.getServiceId());
    //     responseDTO.setPetSitterId(service.getPetSitter().getPetSitterId());
    //     responseDTO.setPetTypeCodeId(service.getPetTypeCodeId());
    //     responseDTO.setPetTypeCodeGroupId(service.getPetTypeCodeGroupId());
    //     responseDTO.setServiceFee(service.getServiceFee());
    //     responseDTO.setLocationCodeId(service.getLocationCodeId());
    //     responseDTO.setLocationCodeGroupId(service.getLocationCodeGroupId());
    //     responseDTO.setPetSizeCodeId(service.getPetSizeCodeId());
    //     responseDTO.setPetSizeCodeGroupId(service.getPetSizeCodeGroupId());
    //     return responseDTO;
    // }

    public void delete(Long serviceId) {
        petSitterServiceRepository.deleteById(serviceId);
    }

    public List<PetSitterServiceResponseDTO> getAllServices() {
        return petSitterServiceRepository.findAll().stream()
            .map(petSitterServiceMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
} 