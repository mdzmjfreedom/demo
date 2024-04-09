package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.database.TestDO;
import com.example.demo.response.TestResponse;

import java.util.List;

public interface TestService extends IService<TestDO> {

    List<TestResponse> queryList();
}
