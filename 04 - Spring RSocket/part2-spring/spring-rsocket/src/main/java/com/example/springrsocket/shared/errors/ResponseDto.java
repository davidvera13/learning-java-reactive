package com.example.springrsocket.shared.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class ResponseDto<T> {
    private ErrorEvent errorResponse;
    T successResponse;


    public ResponseDto(ErrorEvent errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ResponseDto(T successResponse) {
        this.successResponse = successResponse;
    }

    public boolean hasErrors() {
        return Objects.nonNull(this.errorResponse);
    }

    public static <T> ResponseDto<T> with(T t) {
        return new ResponseDto<>(t);
    }

    public static <T> ResponseDto<T> with(ErrorEvent errorResponse) {
        return new ResponseDto<>(errorResponse);
    }
}
