package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.petpal.petpalapp.domain.Pet;
import com.petpal.petpalapp.dto.request.PetRequestDTO;
import com.petpal.petpalapp.dto.response.PetResponseDTO;

@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(source = "user.userId", target = "userId")
    PetResponseDTO toResponseDTO(Pet pet);
    
    Pet toEntity(PetRequestDTO requestDTO);
} 