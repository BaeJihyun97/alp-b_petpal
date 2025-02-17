package com.petpal.petpalapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PetRequestDTO;
import com.petpal.petpalapp.dto.response.PetResponseDTO;
import com.petpal.petpalapp.service.PetService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PetResponseDTO>>> getAllPets() {
        List<PetResponseDTO> pets = petService.getAllPets();
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫 목록 조회 성공", pets));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PetResponseDTO>> getPetById(@PathVariable Long id) {
        PetResponseDTO pet = petService.getPetById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫 조회 성공", pet));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO<List<PetResponseDTO>>> getPetsByUserId(@PathVariable Long userId) {
        List<PetResponseDTO> pets = petService.getPetsByUserId(userId);
        return ResponseEntity.ok(new ResponseDTO<>(200, "사용자의 펫 목록 조회 성공", pets));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ResponseDTO<List<PetResponseDTO>>> getPetsByOwnerId(@PathVariable Long ownerId) {
        List<PetResponseDTO> pets = petService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(new ResponseDTO<>(200, "주인의 반려동물 목록 조회 성공", pets));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PetResponseDTO>> createPet(@Valid @RequestBody PetRequestDTO petRequest) {
        PetResponseDTO pet = petService.createPet(petRequest);
        return ResponseEntity.ok(new ResponseDTO<>(200, "반려동물 등록 성공", pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PetResponseDTO>> updatePet(
            @PathVariable Long id, 
            @Valid @RequestBody PetRequestDTO petRequest) {
        PetResponseDTO pet = petService.updatePet(id, petRequest);
        return ResponseEntity.ok(new ResponseDTO<>(200, "반려동물 정보 수정 성공", pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "펫 삭제 성공", null));
    }
} 