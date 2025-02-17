package com.petpal.petpalapp.mapper;

import com.petpal.petpalapp.domain.code.CodeDetail;
import com.petpal.petpalapp.dto.request.CodeDetailRequestDTO;
import com.petpal.petpalapp.dto.response.CodeDetailResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CodeDetailMapper {
    
    public CodeDetail toEntity(CodeDetailRequestDTO dto) {
        CodeDetail entity = new CodeDetail();
        entity.setCodeId(dto.getCodeId());
        entity.setGroupId(dto.getGroupId());
        entity.setCodeName(dto.getCodeName());
        entity.setCodeValue(dto.getCodeValue());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }

    public CodeDetailResponseDTO toResponseDto(CodeDetail entity) {
        CodeDetailResponseDTO responseDto = new CodeDetailResponseDTO();
        responseDto.setCodeId(entity.getCodeId());
        responseDto.setGroupId(entity.getGroupId());
        responseDto.setCodeName(entity.getCodeName());
        responseDto.setCodeValue(entity.getCodeValue());
        responseDto.setSortOrder(entity.getSortOrder());
        responseDto.setIsActive(entity.getIsActive());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setUpdatedAt(entity.getUpdatedAt());
        return responseDto;
    }

    public List<CodeDetailResponseDTO> toResponseDtoList(List<CodeDetail> entities) {
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
} 