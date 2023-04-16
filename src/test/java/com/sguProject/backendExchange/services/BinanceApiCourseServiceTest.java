package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.services.interfaces.CurrencyPairService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class BinanceApiCourseServiceTest {
    private final BinanceApiCourseService binanceApiCourseService;
    private final CurrencyPairService currencyPairService;

    @Autowired
    BinanceApiCourseServiceTest(BinanceApiCourseService binanceApiCourseService,
                                CurrencyPairService currencyPairService) {
        this.binanceApiCourseService = binanceApiCourseService;
        this.currencyPairService = currencyPairService;
    }

    @Test
    void getCourse_BTCUSDT_GreaterThan0() {
        CurrencyPair currencyPair = currencyPairService.getByBaseAndQuoted("BTC", "USDT");
        double course = binanceApiCourseService.getCourse(currencyPair);

        System.out.println(course);
        assertTrue(course > 0);
    }
}