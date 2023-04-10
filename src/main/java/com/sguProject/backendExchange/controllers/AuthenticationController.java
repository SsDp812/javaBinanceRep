package com.sguProject.backendExchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String LOGIN = "auth/login";
    private static final String LOGOUT = "auth/logout";

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN;
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return LOGOUT;
    }
}
