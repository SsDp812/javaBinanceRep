package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.CurrencyPair;

public interface CourseService {
    double getCourse(CurrencyPair currencyPair);
}
