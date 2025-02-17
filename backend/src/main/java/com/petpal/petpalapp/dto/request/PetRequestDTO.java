package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class PetRequestDTO {
    private String petName;
    private int petAge;
    private Long petOwnerId;
    private String petTypeCodeId;
    private String petTypeCodeGroupId;
    private String petSizeCodeId;
    private String petSizeCodeGroupId;
} 