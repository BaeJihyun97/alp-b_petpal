package com.petpal.petpalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<T> {
    private int status; // HTTP 상태 코드
    private String message; // 응답 메시지
    private T data; // 응답 데이터 (제네릭)
}
