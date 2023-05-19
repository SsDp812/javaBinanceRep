package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Currency;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.util.exception.CourseNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinanceApiCourseServiceTest {
    private static BinanceApiCourseService binanceApiCourseService;

    @BeforeClass
    public static void initialize() {
        binanceApiCourseService = new BinanceApiCourseService();
    }

    @Test
    public void getCourse_whenBtcUsdt_thenGreaterThan0() {
        CurrencyPair currencyPair = new CurrencyPair(new Currency("BTC"), new Currency("USDT"));
        double course = binanceApiCourseService.getCourse(currencyPair);

        assertTrue(course > 0);
    }

    @Test(expected = CourseNotFoundException.class)
    public void getCourse_whenUsdtBtc_thenThrowException() {
        CurrencyPair currencyPair = new CurrencyPair(new Currency("USDT"), new Currency("BTC"));
        double course = binanceApiCourseService.getCourse(currencyPair);
    }
}