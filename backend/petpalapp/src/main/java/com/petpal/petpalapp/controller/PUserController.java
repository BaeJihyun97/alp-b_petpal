package com.petpal.petpalapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PUserRegistDTO;
import com.petpal.petpalapp.dto.request.PUserUpdateDTO;
import com.petpal.petpalapp.service.PUserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "유저 API", description = "유저 컨트롤러에 대한 설명입니다.")
public class PUserController {
    private final PUserService pUserService;

    @Autowired
    public PUserController(PUserService pUserService) {
        this.pUserService = pUserService;
    }

    // C
    @PostMapping
    public ResponseEntity<ResponseDTO<PUser>> createPUser(@RequestBody PUserRegistDTO user) {
        ResponseDTO<PUser> response = pUserService.createPUser(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // R list
    @GetMapping
    public ResponseEntity<ResponseDTO<List<PUser>>> getAllPUsers() {
        ResponseDTO<List<PUser>> response = pUserService.getAllPUsers();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PUser>> getPUserById(@PathVariable("id") Long id) {
        ResponseDTO<PUser> response = pUserService.getPUserById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // U
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PUser>> updatePUser(@PathVariable("id") Long id,
            @RequestBody PUserUpdateDTO user) {
        ResponseDTO<PUser> response = pUserService.updatePUser(id, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // D
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deletePUser(@PathVariable("id") Long id) {
        ResponseDTO<Void> response = pUserService.deletePUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
