package com.petpal.petpalapp.common;

import com.petpal.petpalapp.dto.ResponseDTO;

public class ResponseBuilder {
    public static <T> ResponseDTO<T> makeMessage(int code, String message, T data) {
        return new ResponseDTO<>(code, message, data);
    }
}