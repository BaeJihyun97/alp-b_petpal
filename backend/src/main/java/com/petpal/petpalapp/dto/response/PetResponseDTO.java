package com.petpal.petpalapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetResponseDTO {
    private Long petId;
    private Long userId;
    private String petName;
    private int petAge;
    private String petTypeCodeId;
    private String petTypeCodeGroupId;
    private String petSizeCodeId;
    private String petSizeCodeGroupId;
} 