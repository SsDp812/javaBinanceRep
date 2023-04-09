package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BinanceApiCourseService implements CourseService {
    private final static String BASE_URL = "https://testnet.binance.vision/api/v3/ticker/price?symbol=";

    @Override
    public double getCourse(CurrencyPair currencyPair) {
        final String url = BASE_URL + currencyPair.getBase().getTicker() + currencyPair.getQuoted().getTicker();

        RestTemplate restTemplate = new RestTemplate();

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(restTemplate.getForObject(url, String.class));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String course = (String) response.get("price");

        return Double.parseDouble(course);
    }
}
