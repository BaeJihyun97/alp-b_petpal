package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.petpal.petpalapp.domain.PetSitter;
import com.petpal.petpalapp.dto.request.PetSitterRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterResponseDTO;

@Mapper(componentModel = "spring")
public interface PetSitterMapper {
    @Mapping(source = "user.userId", target = "userId")
    PetSitterResponseDTO toResponseDTO(PetSitter petSitter);
    
    PetSitter toEntity(PetSitterRequestDTO requestDTO);
} 