package com.sguProject.backendExchange.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BinanceApiCourseService implements CourseService {
    private final static String BASE_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

    @Override
    public double getCourse(CurrencyPair currencyPair) {
        final String url = BASE_URL + currencyPair.getBase().getTicker() + currencyPair.getQuoted().getTicker();

        RestTemplate restTemplate = new RestTemplate();

        JsonObject response = JsonParser.parseString(restTemplate.getForObject(url, String.class)).getAsJsonObject();

        return response.get("price").getAsDouble();
    }
}
