package com.petpal.petpalapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    @Schema(description = "사용자 아이디", example = "1234")
    private Long userId;

    @Schema(description = "사용자 이름", example = "JohnDoe")
    private String name;

    @Schema(description = "이메일", example = "john@example.com")
    private String email;
}
