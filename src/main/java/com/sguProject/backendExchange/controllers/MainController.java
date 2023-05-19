package com.sguProject.backendExchange.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/main", "/"})
public class MainController {

    private static final String MAIN = "main";

    @GetMapping()
    public String index() {
        return MAIN;
    }
}
