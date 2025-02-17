package com.petpal.petpalapp.domain.code;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CodeDetailId implements Serializable {
    private String codeId;
    private String groupId;
} 