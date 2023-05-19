package com.sguProject.backendExchange.controllers;

import com.sguProject.backendExchange.models.Account;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.util.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String LOGIN = "auth/login";
    private static final String LOGOUT = "auth/logout";
    private static final String REGISTRATION = "auth/registration";

    private final AccountValidator accountValidator;

    private final AccountService accountService;

    @Autowired
    public AuthenticationController(AccountValidator accountValidator, AccountService accountService) {
        this.accountValidator = accountValidator;
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN;
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return LOGOUT;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("account") Account account) {
        return REGISTRATION;
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("account") @Valid Account account,
                                      BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors())
            return REGISTRATION;

        accountService.register(account);

        return "redirect:/auth/login";
    }
}
