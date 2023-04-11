package com.sguProject.backendExchange.util;

import com.sguProject.backendExchange.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.sguProject.backendExchange.models.Account;

import java.util.Optional;

@Component
public class AccountValidator implements Validator {

    private AccountService accountService;

    @Autowired
    public AccountValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;

        Optional<Account> accountEqualsUsername = accountService.findByUsername(account.getUsername());

        if (accountEqualsUsername.isPresent())
            errors.rejectValue("username", "Account with that username already exist");
    }
}
