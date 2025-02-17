package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetSitterRequestDTO {
    private Long userId;
    private String locationCodeId;
    private String locationCodeGroupId;
    private String nickname;
    private String phoneNumber;
    private String introduction;
} 