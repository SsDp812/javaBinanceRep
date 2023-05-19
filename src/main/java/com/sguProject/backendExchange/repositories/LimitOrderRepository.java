package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.models.LimitOrder;
import com.sguProject.backendExchange.util.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitOrderRepository extends JpaRepository<LimitOrder, Integer> {
    List<LimitOrder> findAllByOwner(Account owner);

    List<LimitOrder> findAllByStatus(OrderStatus status);
}
