package com.petpal.petpalapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.service.PUserService;

import io.swagger.v3.oas.annotations.tags.Tag;

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
    public ResponseEntity<PUser> updatePassword(@RequestBody PUser user) {
        Optional<PUser> updatedUser = pUserService.updatePasswordPUser(user);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
