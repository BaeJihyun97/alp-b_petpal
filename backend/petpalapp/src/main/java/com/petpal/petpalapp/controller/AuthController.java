package com.petpal.petpalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalapp.dto.ResponseDTO;
import com.petpal.petpalapp.dto.request.PUserRequestDTO;
import com.petpal.petpalapp.dto.response.LoginResponseDTO;
import com.petpal.petpalapp.service.PUserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "비밀번호 API", description = "비밀번호 컨트롤러에 대한 설명입니다.")
public class AuthController {
    private final PUserService pUserService;

    @Autowired
    public AuthController(PUserService pUserService) {
        this.pUserService = pUserService;
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<ResponseDTO<Void>> updatePassword(@RequestBody PUserRequestDTO user) {
        ResponseDTO<Void> updatedUser = pUserService.updatePassword(user);
        return ResponseEntity.status(updatedUser.getStatus()).body(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(@RequestBody PUserRequestDTO request,
            HttpSession session) {
        ResponseDTO<LoginResponseDTO> response = pUserService.login(request, session);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<Void>> logout(HttpSession session) {
        ResponseDTO<Void> response = pUserService.logout(session);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/session")
    public ResponseEntity<ResponseDTO<Void>> checkSession(HttpSession session) {
        ResponseDTO<Void> response = pUserService.checkSession(session);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
