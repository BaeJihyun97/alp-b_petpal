package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeGroupRequestDTO {
    private String groupId;
    private String groupName;
    private String parentGroupId;
    private String description;
} 