package com.example.demo.config.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求的公网IP地址
        String remoteIp = servletRequest.getRemoteAddr();

        // 这里你可以记录或处理公网IP地址，例如将其存储到日志中
        System.out.println("Public IP Address: " + remoteIp);

        // 继续请求链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
