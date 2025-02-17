package com.petpal.petpalapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PetSitterServiceRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterServiceResponseDTO;
import com.petpal.petpalapp.service.PetSitterServiceService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.petpal.petpalapp.domain.PetSitterService;
import com.petpal.petpalapp.domain.PetSitter;
import com.petpal.petpalapp.repository.PetSitterServiceRepository;

@RestController
@RequestMapping("/api/pet-sitter-services")
@RequiredArgsConstructor
public class PetSitterServiceController {
    private final PetSitterServiceService petSitterServiceService;
    private final PetSitterServiceRepository petSitterServiceRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PetSitterServiceResponseDTO>>> getAllServices() {
        List<PetSitterServiceResponseDTO> services = petSitterServiceService.getAllServices();
        return ResponseEntity.ok(new ResponseDTO<>(200, "서비스 목록 조회 성공", services));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<PetSitterServiceResponseDTO> getService(@PathVariable Long serviceId) {
        PetSitterServiceResponseDTO service = petSitterServiceService.findById(serviceId);
        return ResponseEntity.ok(service);
    }

    @GetMapping("/pet-sitter/{petSitterId}")
    public ResponseEntity<List<PetSitterServiceResponseDTO>> getServicesByPetSitterId(
            @PathVariable Long petSitterId) {
        List<PetSitterServiceResponseDTO> services = petSitterServiceService.findByPetSitterId(petSitterId);
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<PetSitterServiceResponseDTO> createService(@RequestBody PetSitterServiceRequestDTO requestDTO) {
        PetSitterServiceResponseDTO savedService = petSitterServiceService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<PetSitterServiceResponseDTO> updateService(
            @PathVariable Long serviceId,
            @RequestBody PetSitterServiceRequestDTO requestDTO) {
        PetSitterServiceResponseDTO updatedService = petSitterServiceService.update(serviceId, requestDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        petSitterServiceService.delete(serviceId);
        return ResponseEntity.noContent().build();
    }
} 