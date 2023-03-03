package com.company.cryptoexchangetask.service.impl;

import com.company.cryptoexchangetask.dto.wallet.ExchangeResponse;
import com.company.cryptoexchangetask.entity.Currency;
import com.company.cryptoexchangetask.entity.Transaction;
import com.company.cryptoexchangetask.entity.Wallet;
import com.company.cryptoexchangetask.entity.user.User;
import com.company.cryptoexchangetask.exception.EntityNotFoundException;
import com.company.cryptoexchangetask.exception.EntitySavingFailedException;
import com.company.cryptoexchangetask.exception.UnableToConvertMoneyException;
import com.company.cryptoexchangetask.exception.UnableToWithdrawMoneyException;
import com.company.cryptoexchangetask.repo.CurrencyRepo;
import com.company.cryptoexchangetask.repo.TransactionRepo;
import com.company.cryptoexchangetask.repo.UserRepo;
import com.company.cryptoexchangetask.repo.WalletRepo;
import com.company.cryptoexchangetask.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;


@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;
    private final UserRepo userRepo;

    private final CurrencyRepo currencyRepo;

    private final TransactionRepo transactionRepo;

    @Override
    public String add(String username, String currencyName) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var currency = currencyRepo.findByCurrencyName(currencyName)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class));
        var wallet = new Wallet(
                user,
                0.,
                currency
        );
        try {
            walletRepo.save(wallet);
        } catch (Exception e) {
            throw new EntitySavingFailedException(Wallet.class);
        }
        return user.getSecretKey();
    }

    @Override
    public double changeWalletBalance(String secretKey, String currencyName, double moneyAmount) throws UnableToWithdrawMoneyException {
        var wallet = walletRepo.findWalletByUserSecretKeyAndCurrencyCurrencyName(
                secretKey,
                currencyName
        ).orElseThrow(() -> new EntityNotFoundException(User.class));
        var newBalance = wallet.getBalance() + moneyAmount;
        if (newBalance < 0) throw new UnableToWithdrawMoneyException(secretKey);
        wallet.setBalance(newBalance);
        walletRepo.save(wallet);
        transactionRepo.save(new Transaction(LocalDateTime.now(), moneyAmount));
        return newBalance;
    }

    @Override
    public double getWalletBalance(String secretKey, String currencyName) {
        var wallet = walletRepo.findWalletByUserSecretKeyAndCurrencyCurrencyName(
                secretKey,
                currencyName
        ).orElseThrow(() -> new EntityNotFoundException(User.class));
        return wallet.getBalance();
    }

    @Override
    public HashMap<String, Double> getAllWalletsBalance(String secretKey) {
        var wallets = walletRepo.findWalletByUserSecretKey(secretKey);
        var namesAndBalances = new HashMap<String, Double>();
        for (Wallet wallet : wallets) {
            var name = wallet.getCurrency().getCurrencyName() + "_wallet";
            namesAndBalances.put(
                    name,
                    wallet.getBalance()
            );
        }
        return namesAndBalances;
    }

    @Override
    public ExchangeResponse exchange(
            String secretKey,
            String inputCurrencyName,
            String outputCurrencyName,
            Double amount,
            Double inputToOtputRelation
    ) {
        var inputWallet = walletRepo.findWalletByUserSecretKeyAndCurrencyCurrencyName(
                secretKey,
                inputCurrencyName
        ).orElseThrow(() -> new EntityNotFoundException(User.class));
        var outputWallet = walletRepo.findWalletByUserSecretKeyAndCurrencyCurrencyName(
                secretKey,
                outputCurrencyName
        ).orElseThrow(() -> new EntityNotFoundException(User.class));
        var inputWalletBalance = inputWallet.getBalance();
        var outputWalletBalance = outputWallet.getBalance();
        if (amount > inputWalletBalance) throw new UnableToConvertMoneyException(
                inputCurrencyName,
                outputCurrencyName,
                secretKey
        );
        var newInputWalletBalance = inputWalletBalance - amount;
        var addition = amount / inputToOtputRelation;
        var newOutputWalletBalance = outputWalletBalance + addition;
        inputWallet.setBalance(newInputWalletBalance);
        outputWallet.setBalance(newOutputWalletBalance);
        return new ExchangeResponse(
                inputCurrencyName,
                outputCurrencyName,
                inputWalletBalance,
                addition
        );
    }

    @Override
    public Wallet findByCurrencyAndKey(String currencyName, String key) {
        return walletRepo.findWalletByUserSecretKeyAndCurrencyCurrencyName(key, currencyName).orElseThrow(() -> new EntityNotFoundException(Wallet.class));
    }
}
