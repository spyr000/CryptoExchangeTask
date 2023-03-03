package com.company.cryptoexchangetask.service;

import com.company.cryptoexchangetask.dto.wallet.ExchangeResponse;
import com.company.cryptoexchangetask.entity.Wallet;
import com.company.cryptoexchangetask.exception.UnableToWithdrawMoneyException;

import java.util.HashMap;

public interface WalletService {
    String add(String username, String currencyName);

    double changeWalletBalance(String secretKey, String currencyName, double moneyAmount)
            throws UnableToWithdrawMoneyException;

    double getWalletBalance(String secretKey, String currencyName);

    HashMap<String, Double> getAllWalletsBalance(String secretKey);

    ExchangeResponse exchange(
            String secretKey,
            String inputCurrencyName,
            String outputCurrencyName,
            Double amount,
            Double inputToOtputRelation
    );

    Wallet findByCurrencyAndKey(String currencyName, String key);
}
