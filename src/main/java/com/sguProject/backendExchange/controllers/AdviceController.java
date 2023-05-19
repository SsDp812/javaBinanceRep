package com.sguProject.backendExchange.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/advices")
public class AdviceController {

    private static final String ADVICES = "advices/advices";
    private static final String STRATEGIES = "advices/strategies";
    private static final String CONCEPTS = "advices/concepts";
    private static final String LESSONS = "advices/lessons";
    private static final String TEST = "advices/test";

    @GetMapping()
    public String index() {
        return ADVICES;
    }

    @GetMapping("/strategies")
    public String strategiesPage() {
        return STRATEGIES;
    }

    @GetMapping("/concepts")
    public String conceptsPage() {
        return CONCEPTS;
    }

    @GetMapping("/lessons")
    public String lessonsPage() {
        return LESSONS;
    }

    @GetMapping("/test")
    public String testPage() {
        return TEST;
    }
}
