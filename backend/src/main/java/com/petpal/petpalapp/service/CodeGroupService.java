package com.petpal.petpalapp.service;

import com.petpal.petpalapp.domain.code.CodeGroup;
import com.petpal.petpalapp.repository.CodeGroupRepository;
import com.petpal.petpalapp.dto.request.CodeGroupRequestDTO;
import com.petpal.petpalapp.dto.response.CodeGroupResponseDTO;
import com.petpal.petpalapp.mapper.CodeGroupMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;
    private final CodeGroupMapper codeGroupMapper;

    public List<CodeGroupResponseDTO> getAllCodeGroups() {
        return codeGroupMapper.toResponseDtoList(codeGroupRepository.findAll());
    }

    public List<CodeGroupResponseDTO> getMainCodeGroups() {
        return codeGroupMapper.toResponseDtoList(
            codeGroupRepository.findByParentGroupIdIsNull()
        );
    }

    public List<CodeGroupResponseDTO> getSubCodeGroups(String parentGroupId) {
        return codeGroupMapper.toResponseDtoList(
            codeGroupRepository.findByParentGroupId(parentGroupId)
        );
    }

    public CodeGroupResponseDTO getCodeGroup(String groupId) {
        return codeGroupMapper.toResponseDto(
            codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Code group not found"))
        );
    }

    @Transactional
    public CodeGroupResponseDTO createCodeGroup(CodeGroupRequestDTO dto) {
        if (codeGroupRepository.existsById(dto.getGroupId())) {
            throw new IllegalStateException("이미 존재하는 그룹 ID입니다: " + dto.getGroupId());
        }

        if (dto.getParentGroupId() != null && !dto.getParentGroupId().trim().isEmpty()) {
            if (!codeGroupRepository.existsById(dto.getParentGroupId())) {
                throw new EntityNotFoundException("Parent group not found");
            }
        }
        if(dto.getParentGroupId() != null && dto.getParentGroupId().trim().isEmpty()) {
            dto.setParentGroupId(null);
        }

        CodeGroup codeGroup = codeGroupMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        codeGroup.setCreatedAt(now);
        codeGroup.setUpdatedAt(now);
        
        return codeGroupMapper.toResponseDto(codeGroupRepository.save(codeGroup));
    }

    @Transactional
    public CodeGroupResponseDTO updateCodeGroup(String groupId, CodeGroupRequestDTO dto) {
        CodeGroup existingGroup = codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Code group not found"));

        if (dto.getParentGroupId() != null && !dto.getParentGroupId().trim().isEmpty()) {
            codeGroupRepository.findById(dto.getParentGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Parent group not found"));
        }
        
        existingGroup.setGroupName(dto.getGroupName());
        existingGroup.setParentGroupId(dto.getParentGroupId());
        existingGroup.setDescription(dto.getDescription());
        existingGroup.setUpdatedAt(LocalDateTime.now());
        
        return codeGroupMapper.toResponseDto(codeGroupRepository.save(existingGroup));
    }

    @Transactional
    public void deleteCodeGroup(String groupId) {
        CodeGroup codeGroup = codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Code group not found"));
        codeGroupRepository.delete(codeGroup);
    }
} 