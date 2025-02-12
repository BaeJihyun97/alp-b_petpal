package com.petpal.petpalapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @Schema(description = "이메일", example = "john@example.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "JohnDoe123")
    private String password;
}