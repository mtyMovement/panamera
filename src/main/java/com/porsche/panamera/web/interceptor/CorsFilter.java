package com.porsche.panamera.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest reqs = (HttpServletRequest) req;
        String curOrigin = reqs.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", curOrigin == null ? "true" : curOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }


    @Override
    public void init(FilterConfig filterConfig) {
        log.info("=== 加载配置允许跨域请求 cookie 成功 ===");
    }

    @Override
    public void destroy() {}

}