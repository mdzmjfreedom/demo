package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Test implements Serializable {

    private Long orderId;

    private String orderNo;

    private int orderStatus;

    private Long siteId;

    private LocalDateTime createdTime;
}
