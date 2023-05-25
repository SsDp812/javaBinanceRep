package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.*;
import com.sguProject.backendExchange.repositories.BalanceRepository;
import com.sguProject.backendExchange.repositories.CurrencyPairRepository;
import com.sguProject.backendExchange.repositories.CurrencyRepository;
import com.sguProject.backendExchange.repositories.TransactionRepository;
import com.sguProject.backendExchange.services.interfaces.AccountService;
import com.sguProject.backendExchange.services.interfaces.BalanceService;
import com.sguProject.backendExchange.services.interfaces.CourseService;
import com.sguProject.backendExchange.services.interfaces.ExchangeService;
import com.sguProject.backendExchange.util.enums.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeServiceImplTest {
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private BalanceService balanceService;

    @MockBean
    private AccountService accountService;
    @MockBean
    private CourseService courseService;
    @MockBean
    private CurrencyRepository currencyRepository;
    @MockBean
    private CurrencyPairRepository currencyPairService;
    @MockBean
    private BalanceRepository balanceRepository;
    @MockBean
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void initialize() {
        Account account = new Account();
        account.setId(0);

        Currency btc = new Currency("BTC");
        Currency usdt = new Currency("USDT");

        CurrencyPair currencyPair = new CurrencyPair(btc, usdt);
        currencyPair.setId(0);

        Balance btcBalance = new Balance();
        btcBalance.setId(0);
        btcBalance.setCurrency(btc);
        btcBalance.setOwner(account);
        btcBalance.setAmount(5);

        Balance usdtBalance = new Balance();
        usdtBalance.setId(1);
        usdtBalance.setCurrency(usdt);
        usdtBalance.setOwner(account);
        usdtBalance.setAmount(50000);

        when(courseService.getCourse(currencyPair)).thenReturn(30000d);

        when(accountService.getProxyAccountCurrentSession()).thenReturn(account);

        when(currencyRepository.findByTicker("BTC")).thenReturn(Optional.of(btc));
        when(currencyRepository.findByTicker("USDT")).thenReturn(Optional.of(usdt));
        when(currencyPairService.findByBaseAndQuoted(btc, usdt)).thenReturn(Optional.of(currencyPair));

        when(balanceRepository.findByOwnerAndCurrency(account, btc)).thenReturn(Optional.of(btcBalance));
        when(balanceRepository.findByOwnerAndCurrency(account, usdt)).thenReturn(Optional.of(usdtBalance));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void exchange_whenBuyOneBtcUsdt_thenBalancesChanged() {

        String baseTicker = "BTC";
        String quotedTicker = "USDT";

        double buyableAmount = getAmount(baseTicker);
        double salableAmount = getAmount(quotedTicker);

        exchangeService.exchange(baseTicker, quotedTicker, 30000, Operation.BUY);

        assertAll(
                () -> assertEquals(1, getAmount(baseTicker) - buyableAmount),
                () -> assertEquals(30000, salableAmount - getAmount(quotedTicker))
        );
    }

    @Test
    void exchange_whenSellOneBtcUsdt_thenBalancesChanged() {

        String baseTicker = "BTC";
        String quotedTicker = "USDT";

        double salableAmount = getAmount(baseTicker);
        double buyableAmount = getAmount(quotedTicker);

        exchangeService.exchange(baseTicker, quotedTicker, 1, Operation.SELL);

        assertAll(
                () -> assertEquals(1, salableAmount - getAmount(baseTicker)),
                () -> assertEquals(30000, getAmount(quotedTicker) - buyableAmount)
        );
    }

    private double getAmount(String ticker) {
        return balanceService.getUserBalanceBy(ticker).getAmount();
    }
}