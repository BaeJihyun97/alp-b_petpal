package com.petpal.petpalapp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PUserRegistDTO {
    @Schema(description = "사용자 비밀번호", example = "JohnDoe123")
    private String password;

    @Schema(description = "사용자 이름", example = "JohnDoe")
    private String name;

    @Schema(description = "이메일", example = "john@petpal.com")
    private String email;

    @Schema(description = "휴대전화번호", example = "01012345678")
    private String phoneNumber;
}
