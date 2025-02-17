package com.petpal.petpalapp.service;

import com.petpal.petpalapp.domain.code.CodeDetail;
import com.petpal.petpalapp.domain.code.CodeDetailId;
import com.petpal.petpalapp.repository.CodeDetailRepository;
import com.petpal.petpalapp.repository.CodeGroupRepository;
import com.petpal.petpalapp.dto.request.CodeDetailRequestDTO;
import com.petpal.petpalapp.dto.response.CodeDetailResponseDTO;
import com.petpal.petpalapp.mapper.CodeDetailMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeDetailService {

    private final CodeDetailRepository codeDetailRepository;
    private final CodeGroupRepository codeGroupRepository;
    private final CodeDetailMapper codeDetailMapper;

    public List<CodeDetailResponseDTO> getCodeDetailsByGroup(String groupId) {
        // 그룹 존재 여부 확인
        if (!codeGroupRepository.existsById(groupId)) {
            throw new EntityNotFoundException("Code group not found");
        }
        return codeDetailMapper.toResponseDtoList(
            codeDetailRepository.findByGroupId(groupId)
        );
    }

    public CodeDetailResponseDTO getCodeDetail(String groupId, String codeId) {
        return codeDetailMapper.toResponseDto(
            codeDetailRepository.findById(new CodeDetailId(codeId, groupId))
                .orElseThrow(() -> new EntityNotFoundException("Code detail not found"))
        );
    }

    @Transactional
    public CodeDetailResponseDTO createCodeDetail(CodeDetailRequestDTO dto) {
        // 그룹 존재 여부 확인
        if (!codeGroupRepository.existsById(dto.getGroupId())) {
            throw new EntityNotFoundException("Code group not found");
        }

        // 코드 ID 중복 검사
        if (codeDetailRepository.existsById(new CodeDetailId(dto.getCodeId(), dto.getGroupId()))) {
            throw new IllegalStateException("이미 존재하는 코드 ID입니다: " + dto.getCodeId());
        }

        CodeDetail codeDetail = codeDetailMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        codeDetail.setCreatedAt(now);
        codeDetail.setUpdatedAt(now);
        codeDetail.setIsActive(true);
        
        return codeDetailMapper.toResponseDto(codeDetailRepository.save(codeDetail));
    }

    @Transactional
    public CodeDetailResponseDTO updateCodeDetail(String groupId, String codeId, CodeDetailRequestDTO dto) {
        System.out.println("updateCodeDetail 호출" + dto.getCodeId());
        // System.out.println("updateCodeDetail 호출" + groupId);
        CodeDetail existingDetail = codeDetailRepository.findById(new CodeDetailId(codeId, groupId))
                .orElseThrow(() -> new EntityNotFoundException("Code detail not found"));
        
        existingDetail.setCodeName(dto.getCodeName());
        existingDetail.setCodeValue(dto.getCodeValue());
        existingDetail.setSortOrder(dto.getSortOrder());
        existingDetail.setIsActive(dto.getIsActive());
        existingDetail.setUpdatedAt(LocalDateTime.now());
        
        return codeDetailMapper.toResponseDto(codeDetailRepository.save(existingDetail));
    }

    @Transactional
    public void deleteCodeDetail(String groupId, String codeId) {
        CodeDetail codeDetail = codeDetailRepository.findById(new CodeDetailId(codeId, groupId))
                .orElseThrow(() -> new EntityNotFoundException("Code detail not found"));
        codeDetailRepository.delete(codeDetail);
    }
} 