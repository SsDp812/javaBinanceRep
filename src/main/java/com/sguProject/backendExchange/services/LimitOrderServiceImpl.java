package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.CurrencyPair;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.repositories.LimitOrderRepository;
import com.sguProject.backendExchange.services.interfaces.LimitOrderService;
import com.sguProject.backendExchange.util.enums.Operation;
import com.sguProject.backendExchange.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LimitOrderServiceImpl implements LimitOrderService {

    private final LimitOrderRepository limitOrderRepository;

    @Autowired
    public LimitOrderServiceImpl(LimitOrderRepository limitOrderRepository) {
        this.limitOrderRepository = limitOrderRepository;
    }

    @Transactional
    @Override
    public LimitOrder create(Account owner, CurrencyPair currencyPair, double quantity, Operation operation, double targetCourse) {
        final LimitOrder limitOrder = new LimitOrder();
        limitOrder.setOwner(owner);
        limitOrder.setCurrencyPair(currencyPair);
        limitOrder.setQuantity(quantity);
        limitOrder.setOperation(operation);
        limitOrder.setTargetCourse(targetCourse);
        limitOrder.setStatus(OrderStatus.CREATED);

        return limitOrderRepository.save(limitOrder);
    }

    @Override
    public List<LimitOrder> getAll() {
        return limitOrderRepository.findAll();
    }

    @Override
    public List<LimitOrder> getAll(OrderStatus status) {
        return limitOrderRepository.findAllByStatus(status);
    }

    @Override
    public List<LimitOrder> getAll(Account owner) {
        return limitOrderRepository.findAllByOwner(owner);
    }
}
