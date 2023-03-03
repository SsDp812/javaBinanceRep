package com.sguProject.backendExchange.repositories;

import com.sguProject.backendExchange.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
