package com.petpal.petpalapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PetSitterRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterResponseDTO;
import com.petpal.petpalapp.service.PetSitterService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/pet-sitters")
@RequiredArgsConstructor
public class PetSitterController {
    private final PetSitterService petSitterService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PetSitterResponseDTO>>> getAllPetSitters() {
        List<PetSitterResponseDTO> petSitters = petSitterService.getAllPetSitters();
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫시터 목록 조회 성공", petSitters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PetSitterResponseDTO>> getPetSitterById(@PathVariable Long id) {
        PetSitterResponseDTO petSitter = petSitterService.getPetSitterById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫시터 조회 성공", petSitter));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO<PetSitterResponseDTO>> getPetSitterByUserId(@PathVariable Long userId) {
        PetSitterResponseDTO petSitter = petSitterService.getPetSitterByUserId(userId);
        return ResponseEntity.ok(new ResponseDTO<>(200, "사용자의 펫시터 정보 조회 성공", petSitter));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PetSitterResponseDTO>> createPetSitter(@RequestBody PetSitterRequestDTO request) {
        PetSitterResponseDTO petSitter = petSitterService.createPetSitter(request);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫시터 등록 성공", petSitter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PetSitterResponseDTO>> updatePetSitter(
            @PathVariable Long id, 
            @RequestBody PetSitterRequestDTO request) {
        PetSitterResponseDTO petSitter = petSitterService.updatePetSitter(id, request);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫시터 정보 수정 성공", petSitter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deletePetSitter(@PathVariable Long id) {
        petSitterService.deletePetSitter(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫시터 삭제 성공", null));
    }
} 