package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeDetailRequestDTO {
    private String codeId;
    private String groupId;
    private String codeName;
    private String codeValue;
    private Integer sortOrder;
    private Boolean isActive;
} 