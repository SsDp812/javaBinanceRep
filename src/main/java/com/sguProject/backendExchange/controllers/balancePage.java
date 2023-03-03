package com.sguProject.backendExchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class balancePage {
    @RequestMapping(value="/balance")
    public ModelAndView welcomepage() {
        return new ModelAndView("balance");
    }
}
