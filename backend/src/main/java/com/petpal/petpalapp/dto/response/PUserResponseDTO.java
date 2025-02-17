package com.petpal.petpalapp.dto.response;

import com.petpal.petpalapp.common.enums.PState;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PUserResponseDTO {
    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String userGroupCodeId;
    private String userGroupCodeGroupId;
    private LocalDateTime lastLogin;
    private LocalDateTime registerDate;
    private PState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 