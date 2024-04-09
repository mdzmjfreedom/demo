package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Boolean status;
    private String code;
    private String message;
    private T result;

    public static <T> Result<T> success() {
        return new Result<>(true, "200", "success", null);
    }

    public static <T> Result<T> success(T result) {
        return new Result<>(true, "200", "success", result);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(false, code, message, null);
    }
}
