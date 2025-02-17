package com.petpal.petpalapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.petpal.petpalapp.domain.PetSitterService;
import com.petpal.petpalapp.dto.request.PetSitterServiceRequestDTO;
import com.petpal.petpalapp.dto.response.PetSitterServiceResponseDTO;

@Mapper(componentModel = "spring")
public interface PetSitterServiceMapper {
    @Mapping(source = "petSitter.petSitterId", target = "petSitterId")
    PetSitterServiceResponseDTO toResponseDTO(PetSitterService service);
    
    PetSitterService toEntity(PetSitterServiceRequestDTO requestDTO);
} 