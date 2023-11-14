package com.sguProject.backendExchange.services.interfaces;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;

import java.util.List;

public interface LimitOrderService {
    LimitOrder create(Account owner, String baseTicker, String quotedTicker, double quantity, Operation operation, double targetCourse);

    List<LimitOrder> getAll(OrderStatus status);

    List<LimitOrder> getAll(Account owner);

    void delete(int id);
}
