package com.example.demo.database;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.handler.AESHandler;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "TEST_ENC", autoResultMap = true)
public class TestDO implements Serializable {

    @TableField(value = "\"name\"", typeHandler = AESHandler.class)
    private String name;

    @TableField(value = "\"age\"")
    private Integer age;
}