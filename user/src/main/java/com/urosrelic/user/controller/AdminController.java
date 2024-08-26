package com.urosrelic.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello from admin ROLE";
    }

}
