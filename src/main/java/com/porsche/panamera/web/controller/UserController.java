package com.porsche.panamera.web.controller;

import com.porsche.panamera.core.common.util.TokenService;
import com.porsche.panamera.core.dal.domain.User;
import com.porsche.panamera.core.service.UserService;
import com.porsche.panamera.web.annotation.NeedAuth;
import com.porsche.panamera.web.annotation.NoAuth;
import com.porsche.panamera.web.pojo.UserPojo;
import com.porsche.panamera.web.response.APIResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("api")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @NoAuth
    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public APIResult login(@RequestBody UserPojo requestUser, HttpServletResponse httpServletResponse) {
        //判空
        if(StringUtils.isEmpty(requestUser.getUserName())
                || StringUtils.isEmpty(requestUser.getPassword())){
            return APIResult.error("400", "账号密码不可为空");
        }

        // 对 html 标签进行转义，防止 XSS 攻击
        String userName = requestUser.getUserName();
        userName = HtmlUtils.htmlEscape(userName);
        requestUser.setUserName(userName);
        log.info("UserController -> login requestUser:{}", requestUser);
        User userResult = userService.userLogin(requestUser);

        if(Objects.nonNull(userResult)){
            String token = tokenService.getToken(userResult);
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");           //可在同一应用服务器内共享cookie
            log.info("userController -> login cookie:{}", cookie.getValue());
            httpServletResponse.addCookie(cookie);
            return APIResult.ok();
        }else{
            String message = "账号密码错误";
            return APIResult.error("400",message);
        }
    }

    @NeedAuth
    @GetMapping(value = "/cancelTest")
    @ResponseBody
    public APIResult cancelTest() {
        //判空
        log.info("UserController -> cancelTest" );
        return APIResult.ok();
    }
}
