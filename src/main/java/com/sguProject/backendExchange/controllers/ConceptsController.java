package com.sguProject.backendExchange.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConceptsController {
    @RequestMapping(value="/Concepts")
    public ModelAndView welcomepage() {
        return new ModelAndView("concepts");
    }
}
