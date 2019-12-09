package com.porsche.panamera.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login() {
        log.info("testController");
        return "testController";
    }
}
