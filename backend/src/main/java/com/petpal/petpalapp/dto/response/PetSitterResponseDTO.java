package com.petpal.petpalapp.dto.response;

import com.petpal.petpalapp.common.enums.PState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetSitterResponseDTO {
    private Long petSitterId;
    private Long userId;
    private String locationCodeId;
    private String locationCodeGroupId;
    private String nickname;
    private String phoneNumber;
    private String introduction;
    private PState state;
} 