package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import org.springframework.web.client.RestTemplate;

public class LocalApiCourseService implements CourseService {
    private final static String BASE_URL = "http://localhost:3004/price?symbol=";

    @Override
    public double getCourse(Currency dividend, Currency divisor) {
        final String url = BASE_URL + dividend.getTicker() + divisor.getTicker();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Double.class);
    }
}
