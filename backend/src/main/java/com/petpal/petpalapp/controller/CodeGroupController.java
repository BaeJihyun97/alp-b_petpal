package com.petpal.petpalapp.controller;

import com.petpal.petpalapp.dto.request.CodeGroupRequestDTO;
import com.petpal.petpalapp.dto.response.CodeGroupResponseDTO;
import com.petpal.petpalapp.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code-groups")
@RequiredArgsConstructor
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @GetMapping
    public ResponseEntity<List<CodeGroupResponseDTO>> getAllCodeGroups() {
        return ResponseEntity.ok(codeGroupService.getAllCodeGroups());
    }

    @GetMapping("/main")
    public ResponseEntity<List<CodeGroupResponseDTO>> getMainCodeGroups() {
        return ResponseEntity.ok(codeGroupService.getMainCodeGroups());
    }

    @GetMapping("/{parentGroupId}/sub")
    public ResponseEntity<List<CodeGroupResponseDTO>> getSubCodeGroups(
            @PathVariable String parentGroupId) {
        return ResponseEntity.ok(codeGroupService.getSubCodeGroups(parentGroupId));
    }
    
    @GetMapping("/{groupId}")
    public ResponseEntity<CodeGroupResponseDTO> getCodeGroup(@PathVariable String groupId) {
        return ResponseEntity.ok(codeGroupService.getCodeGroup(groupId));
    }

    @PostMapping
    public ResponseEntity<CodeGroupResponseDTO> createCodeGroup(@RequestBody CodeGroupRequestDTO dto) {
        return ResponseEntity.ok(codeGroupService.createCodeGroup(dto));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<CodeGroupResponseDTO> updateCodeGroup(
            @PathVariable String groupId,
            @RequestBody CodeGroupRequestDTO dto) {
        return ResponseEntity.ok(codeGroupService.updateCodeGroup(groupId, dto));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteCodeGroup(@PathVariable String groupId) {
        codeGroupService.deleteCodeGroup(groupId);
        return ResponseEntity.ok().build();
    }
} 