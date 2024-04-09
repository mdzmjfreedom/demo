package com.example.demo.controller;

import cn.hutool.json.JSONUtil;
import com.example.demo.common.Result;
import com.example.demo.request.TestRequest;
import com.example.demo.response.TestResponse;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/insert")
    public Result<Void> insert(@RequestBody TestRequest request) {
        log.info("插入入参:{}", JSONUtil.toJsonStr(request));
        testService.insert(request);
        return Result.ok();
    }

    @GetMapping("/getList")
    public Result<List<TestResponse>> getList(String name) throws Exception {
//        log.info("查询入参:{}", name);
//        return Result.ok(testService.getList(name));
//        throw new Exception("系统繁忙！");

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info(ip);
        return Result.ok();
    }
}
