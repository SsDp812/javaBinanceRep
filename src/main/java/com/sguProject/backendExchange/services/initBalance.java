package com.sguProject.backendExchange.services;

import com.sguProject.backendExchange.models.Coin;

public class initBalance {
    public initBalance(){

    }
    public void init(){
        BalanceDAO dao = new BalanceDAO();
        dao.save(new Coin(Coin.CoinType.BTC,0));
        dao.save(new Coin(Coin.CoinType.ETH,56));
        dao.save(new Coin(Coin.CoinType.USDT,1000));
        dao.save(new Coin(Coin.CoinType.XRP,927.3));
        dao.save(new Coin(Coin.CoinType.LTC,120));
        dao.save(new Coin(Coin.CoinType.BNB,1000));
        dao.save(new Coin(Coin.CoinType.BCH,713.4));
    }

}
