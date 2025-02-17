// package com.petpal.petpalapp.service;

// import com.petpal.petpalapp.domain.code.PetTypeCode;
// import com.petpal.petpalapp.repository.PetTypeCodeRepository;
// import com.petpal.petpalapp.dto.request.PetTypeRequestDTO;
// import com.petpal.petpalapp.dto.response.PetTypeResponseDTO;
// import com.petpal.petpalapp.mapper.PetTypeCodeMapper;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import jakarta.persistence.EntityNotFoundException;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// @Service
// @RequiredArgsConstructor
// @Transactional(readOnly = true)
// public class PetTypeCodeService {

//     private final PetTypeCodeRepository petTypeCodeRepository;
//     private final PetTypeCodeMapper petTypeCodeMapper;

//     public PetTypeResponseDTO getPetType(String code) {
//         PetTypeCode petTypeCode = petTypeCodeRepository.findById(code)
//                 .orElseThrow(() -> new EntityNotFoundException(
//                     String.format("Pet type code not found with code: %s", code)
//                 ));
//         return petTypeCodeMapper.toResponseDto(petTypeCode);
//     }  

//     public List<PetTypeResponseDTO> getAllPetTypes() {
//         return petTypeCodeMapper.toResponseDtoList(petTypeCodeRepository.findAll());
//     }

//     public List<PetTypeResponseDTO> getMainPetTypes() {
//         return petTypeCodeMapper.toResponseDtoList(petTypeCodeRepository.findByParentIsNull());
//     }

//     public List<PetTypeResponseDTO> getSubPetTypes(String parentCode) {
//         PetTypeCode parent = petTypeCodeRepository.findById(parentCode)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid parent code"));
//         return petTypeCodeMapper.toResponseDtoList(petTypeCodeRepository.findByParent(parent));
//     }

//     @Transactional
//     public PetTypeResponseDTO createPetTypeCode(PetTypeRequestDTO dto) {
//         PetTypeCode petTypeCode = petTypeCodeMapper.toEntity(dto);
//         petTypeCode.setParent(petTypeCodeRepository.findById(dto.getParentTypeCode()).orElse(null));
//         LocalDateTime now = LocalDateTime.now();
//         petTypeCode.setCreatedAt(now);
//         petTypeCode.setUpdatedAt(now);
//         return petTypeCodeMapper.toResponseDto(petTypeCodeRepository.save(petTypeCode));
//     }

//     @Transactional
//     public PetTypeResponseDTO updatePetTypeCode(String code, PetTypeRequestDTO dto) {
//         PetTypeCode existingCode = petTypeCodeRepository.findById(code)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid pet type code"));
        
//         PetTypeCode updateCode = petTypeCodeMapper.toEntity(dto);
//         existingCode.setPetTypeName(updateCode.getPetTypeName());
//         existingCode.setDescription(updateCode.getDescription());
//         existingCode.setParent(updateCode.getParent());
//         existingCode.setUpdatedAt(LocalDateTime.now());
        
//         return petTypeCodeMapper.toResponseDto(petTypeCodeRepository.save(existingCode));
//     }

//     @Transactional
//     public void deletePetTypeCode(String code) {
//         PetTypeCode petTypeCode = petTypeCodeRepository.findById(code)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid pet type code"));
        
//         deleteRecursively(petTypeCode);
//     }

//     private void deleteRecursively(PetTypeCode petTypeCode) {
//         // 먼저 모든 자식들을 찾습니다
//         List<PetTypeCode> children = petTypeCodeRepository.findByParent(petTypeCode);
        
//         // 각 자식에 대해 재귀적으로 삭제를 수행합니다
//         for (PetTypeCode child : children) {
//             deleteRecursively(child);
//         }
        
//         // 자식들을 모두 삭제한 후 현재 노드를 삭제합니다
//         petTypeCodeRepository.delete(petTypeCode);
//     }
// } 