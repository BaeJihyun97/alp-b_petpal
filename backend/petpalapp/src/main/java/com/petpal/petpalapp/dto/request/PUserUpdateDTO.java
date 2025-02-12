package com.petpal.petpalapp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PUserUpdateDTO {

    @Schema(description = "사용자 이름", example = "JohnDoe")
    private String name;

    @Schema(description = "사용자 휴대전화번호호", example = "01012341234")
    private String phoneNumber;
}
