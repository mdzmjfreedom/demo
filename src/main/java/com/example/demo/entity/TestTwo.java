package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TestTwo implements Serializable {

    private Long orderId;

    private String orderNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
