package com.petpal.petpalapp.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CodeGroupResponseDTO {
    private String groupId;
    private String groupName;
    private String parentGroupId;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 