package com.groupc.fourparks.api.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public String login() {
        return "login";
    }

    @GetMapping("/hello-secured")
    public String loginSecured() {
        return "login secured - vista para usuario";
    }

    @GetMapping("/hello-secured-2")
    public String loginSecured2() {
        return "login secured - vista para admin";
    }

}
