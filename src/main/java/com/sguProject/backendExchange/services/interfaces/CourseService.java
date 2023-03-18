package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Currency;

public interface CourseService {
    double getCourse(Currency dividend, Currency divisor);
}
