package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Coin;
import com.sguProject.backendExchange.models.Coin.CoinType;
import com.sguProject.backendExchange.models.Transaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BalanceLogic {
    private BalanceDAO dalanceDao;
    private TransactionDAO transactionDAO;
    public BalanceLogic(BalanceDAO dalanceDao, TransactionDAO transactionDAO) {
        this.dalanceDao = dalanceDao;
        this.transactionDAO = transactionDAO;
    }

    public void changeCoins(CoinType salable, CoinType buyable, double number,String mode) {
        double course = 0;
        if(mode=="BUY"){
            course = getCourse(salable,buyable);
        } else if (mode=="SELL") {
            course = 1 / getCourse(salable,buyable);
            CoinType temp = salable;
            salable = buyable;
            buyable = temp;
        }
        double requiredBuyableCoins = course * number;
        Coin salableCoin = dalanceDao.getCoin(salable);
        double currentSalableCoins = salableCoin.getBalance();
        if (number > currentSalableCoins) {
            throw new IllegalArgumentException("numberPurchased less than need salable coins for success trade");
        }
        Coin buyableCoin = dalanceDao.getCoin(buyable);
        salableCoin.decrease(number);
        buyableCoin.add(requiredBuyableCoins);
        dalanceDao.save(salableCoin);
        dalanceDao.save(buyableCoin);
        salableCoin.setBalance(number);
        buyableCoin.setBalance(requiredBuyableCoins);
        var transaction = new Transaction(buyableCoin, salableCoin, course);
        transactionDAO.save(transaction);
    }

    public double getCourse(CoinType buyable, CoinType salable) {
        double course = 0;
        try {
            URL url = new URL("http://localhost:3004/price?symbol="+ buyable.name() + salable.name());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (var in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())))
            {
                course = Double.parseDouble(in.readLine());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }
}
