package com.example.demo.config.annotations.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.example.demo.config.annotations.Auth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Pointcut("@within(com.example.demo.config.annotations.Auth) || @annotation(com.example.demo.config.annotations.Auth)")
    public void authPoint() {

    }

    @Before("authPoint()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Auth annotation = method.getAnnotation(Auth.class);
        if (ObjectUtil.isEmpty(annotation)) {
            annotation = method.getDeclaringClass().getAnnotation(Auth.class);
        }
        log.info(annotation.message());
    }

    @After("authPoint()")
    public void after(JoinPoint joinPoint) {
    }
}
