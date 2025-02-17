package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetSitterServiceRequestDTO {
    private Long petSitterId;
    private String petTypeCodeId;
    private String petTypeCodeGroupId;
    private String serviceFee;
    private String locationCodeId;
    private String locationCodeGroupId;
    private String petSizeCodeId;
    private String petSizeCodeGroupId;
} 