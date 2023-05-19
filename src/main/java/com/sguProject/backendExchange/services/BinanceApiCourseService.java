package com.sguProject.backendExchange.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import com.sguProject.backendExchange.util.exception.CourseNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class BinanceApiCourseService implements CourseService {
    private final static String BASE_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

    @Override
    public double getCourse(CurrencyPair currencyPair) {
        final String url = BASE_URL + currencyPair.getBase().getTicker() + currencyPair.getQuoted().getTicker();

        RestTemplate restTemplate = new RestTemplate();
        String responseString;

        try {
            responseString = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            throw new CourseNotFoundException("Invalid currency pair: " + currencyPair, e);
        }
        catch (ResourceAccessException e) {
            throw new CourseNotFoundException("No connection to API", e);
        }

        JsonObject response = JsonParser.parseString(Objects.requireNonNull(responseString)).getAsJsonObject();

        return response.get("price").getAsDouble();
    }
}
