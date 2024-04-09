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

    private Boolean success;

    private String message;

    private String code;

    private T result;

    public static <T> Result<T> ok() {
        return new Result<>(true, "success", "200", null);
    }

    public static <T> Result<T> ok(T result) {
        return new Result<>(true, "success", "200", result);
    }

    public static <T> Result<T> error(String message, String code) {
        return new Result<>(false, message, code, null);
    }
}
