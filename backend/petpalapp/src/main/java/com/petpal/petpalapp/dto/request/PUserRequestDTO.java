package com.petpal.petpalapp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PUserRequestDTO {
    @Schema(description = "이메일", example = "puser7@petpal.com")
    private String email;

    @Schema(description = "사용자 비밀번호", example = "puser1123")
    private String password;
}