package com.example.demo.database;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "cloud_test")
public class TestDO implements Serializable {

    private Long id;

    private String name;
}