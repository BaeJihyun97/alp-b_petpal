package com.petpal.petpalapp.controller;

import com.petpal.petpalapp.dto.request.CodeDetailRequestDTO;
import com.petpal.petpalapp.dto.response.CodeDetailResponseDTO;
import com.petpal.petpalapp.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code-details")
@RequiredArgsConstructor
public class CodeDetailController {

    private final CodeDetailService codeDetailService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<CodeDetailResponseDTO>> getCodeDetailsByGroup(
            @PathVariable String groupId) {
        return ResponseEntity.ok(codeDetailService.getCodeDetailsByGroup(groupId));
    }

    @GetMapping("/{groupId}/{codeId}")
    public ResponseEntity<CodeDetailResponseDTO> getCodeDetail(
            @PathVariable String groupId,
            @PathVariable String codeId) {
        return ResponseEntity.ok(codeDetailService.getCodeDetail(groupId, codeId));
    }

    @PostMapping
    public ResponseEntity<CodeDetailResponseDTO> createCodeDetail(
            @RequestBody CodeDetailRequestDTO dto) {
        return ResponseEntity.ok(codeDetailService.createCodeDetail(dto));
    }

    @PutMapping("/{groupId}/{codeId}")
    public ResponseEntity<CodeDetailResponseDTO> updateCodeDetail(
            @PathVariable String groupId,
            @PathVariable String codeId,
            @RequestBody CodeDetailRequestDTO dto) {
        return ResponseEntity.ok(codeDetailService.updateCodeDetail(groupId, codeId, dto));
    }

    @DeleteMapping("/{groupId}/{codeId}")
    public ResponseEntity<Void> deleteCodeDetail(
            @PathVariable String groupId,
            @PathVariable String codeId) {
        codeDetailService.deleteCodeDetail(groupId, codeId);
        return ResponseEntity.ok().build();
    }
} 