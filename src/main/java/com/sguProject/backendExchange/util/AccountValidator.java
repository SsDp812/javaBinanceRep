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

    private static final int MINIMUM_PASSWORD_LENGTH = 5;
    private static final int MAXIMUM_PASSWORD_LENGTH = 30;

    private final AccountService accountService;

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
            errors.rejectValue("username", "","Account with that username already exist");

        int passwordLength = account.getPassword().length();
        if (passwordLength < MINIMUM_PASSWORD_LENGTH || passwordLength > MAXIMUM_PASSWORD_LENGTH)
            errors.rejectValue("password", "",
                    "Password should be between " + MINIMUM_PASSWORD_LENGTH + " and " +
                            MAXIMUM_PASSWORD_LENGTH + " characters");
    }
}
