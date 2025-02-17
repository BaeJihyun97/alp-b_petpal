package com.petpal.petpalapp.dto;

import java.time.LocalDateTime;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

public class PUserDTO {

    @Schema(description = "사용자 아이디", example = "1234")
    private Long userId;

    @Schema(description = "사용자 비밀번호", example = "JohnDoe123")
    private String password;

    @Schema(description = "사용자 이름", example = "JohnDoe")
    private String name;

    @Schema(description = "이메일", example = "john@example.com")
    private String email;

    @Schema(description = "마지막 로그인 날짜")
    private LocalDateTime lastLogin;

}
