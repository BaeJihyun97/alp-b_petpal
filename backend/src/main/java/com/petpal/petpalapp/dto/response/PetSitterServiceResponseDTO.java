package com.petpal.petpalapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetSitterServiceResponseDTO {
    private Long serviceId;
    private Long petSitterId;
    private String petTypeCodeId;
    private String petTypeCodeGroupId;
    private String serviceFee;
    private String locationCodeId;
    private String locationCodeGroupId;
    private String petSizeCodeId;
    private String petSizeCodeGroupId;
} 