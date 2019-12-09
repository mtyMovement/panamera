package com.porsche.panamera.web.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.porsche.panamera.core.dal.domain.User;
import com.porsche.panamera.core.service.UserService;
import com.porsche.panamera.web.annotation.NeedAuth;
import com.porsche.panamera.web.annotation.NoAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.info("=== 进入拦截 ===");
        String token = "";
        //获取cookie, 匹配token
        Cookie[] cookies =  httpServletRequest.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有NoAuth注释，有则跳过认证
        if (method.isAnnotationPresent(NoAuth.class)) {
            NoAuth passToken = method.getAnnotation(NoAuth.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(NeedAuth.class)) {
            NeedAuth userLoginToken = method.getAnnotation(NeedAuth.class);
            if (userLoginToken.required()) {
                // 执行认证
                log.info("=== 执行认证 ===");
                if (StringUtils.isEmpty(token)) {
                    log.warn("Interceptor : token is empty");
                    httpServletResponse.sendError(500);
                    //httpServletResponse.sendRedirect("http://localhost:8078/login");
                    return false;
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    log.info("=== Interceptor -> token: {} ===", token);
                    userId = JWT.decode(token).getAudience().get(0);
                    log.info("=== Interceptor -> token userId: {} ===", userId);
                } catch (JWTDecodeException j) {
                    return false;
                }
                User user = userService.getUserById(Integer.valueOf(userId));
                log.info("=== Interceptor -> token user: {} ===", user);
                if (Objects.isNull(user)) {
                    log.warn("Interceptor : user is null");
                    httpServletResponse.sendRedirect("http://localhost:8078/login");
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}
