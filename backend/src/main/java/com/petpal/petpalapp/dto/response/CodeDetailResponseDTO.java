package com.petpal.petpalapp.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CodeDetailResponseDTO {
    private String codeId;
    private String groupId;
    private String codeName;
    private String codeValue;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 