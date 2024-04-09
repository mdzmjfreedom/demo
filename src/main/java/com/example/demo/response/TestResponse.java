package com.example.demo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

}
