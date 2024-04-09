package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.config.annotations.Auth;
import com.example.demo.response.TestResponse;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/queryList")
    @Auth
    public Result<List<TestResponse>> queryList() {
        return Result.success(testService.queryList());
    }
}
