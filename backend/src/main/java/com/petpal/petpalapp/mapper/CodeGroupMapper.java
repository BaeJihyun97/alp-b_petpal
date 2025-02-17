package com.petpal.petpalapp.mapper;

import com.petpal.petpalapp.domain.code.CodeGroup;
import com.petpal.petpalapp.dto.request.CodeGroupRequestDTO;
import com.petpal.petpalapp.dto.response.CodeGroupResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CodeGroupMapper {
    
    public CodeGroup toEntity(CodeGroupRequestDTO dto) {
        CodeGroup entity = new CodeGroup();
        entity.setGroupId(dto.getGroupId());
        entity.setGroupName(dto.getGroupName());
        entity.setParentGroupId(dto.getParentGroupId());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public CodeGroupResponseDTO toResponseDto(CodeGroup entity) {
        CodeGroupResponseDTO responseDto = new CodeGroupResponseDTO();
        responseDto.setGroupId(entity.getGroupId());
        responseDto.setGroupName(entity.getGroupName());
        responseDto.setParentGroupId(entity.getParentGroupId());
        responseDto.setDescription(entity.getDescription());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setUpdatedAt(entity.getUpdatedAt());
        return responseDto;
    }

    public List<CodeGroupResponseDTO> toResponseDtoList(List<CodeGroup> entities) {
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
} 