package com.sguProject.backendExchange.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class historyPage {
    @RequestMapping(value="/historyPage")
    public ModelAndView getPage() {
        return new ModelAndView("history");
    }
}
