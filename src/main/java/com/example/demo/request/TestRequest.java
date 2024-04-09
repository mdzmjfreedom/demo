package com.example.demo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;
}
