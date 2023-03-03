package com.company.cryptoexchangetask.controller;

import com.company.cryptoexchangetask.dto.transaction.TransactionBalanceRequest;
import com.company.cryptoexchangetask.dto.wallet.ExchangeRequest;
import com.company.cryptoexchangetask.dto.wallet.ExchangeResponse;
import com.company.cryptoexchangetask.dto.wallet.WalletRequest;
import com.company.cryptoexchangetask.dto.wallet.WithdrawCurrencyRequest;
import com.company.cryptoexchangetask.exception.InvalidMoneyAmountException;
import com.company.cryptoexchangetask.exception.NoAuthHeaderException;
import com.company.cryptoexchangetask.exception.ParseJsonException;
import com.company.cryptoexchangetask.exception.UnableToAccessToForeignerWalletException;
import com.company.cryptoexchangetask.service.*;
import com.company.cryptoexchangetask.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/wallets")
public class WalletController {
    private final WalletService walletService;

    private final ExchangeRateService exchangeRateService;

    private final JwtService jwtService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createWallet(
            @RequestBody WalletRequest walletRequest
    ) throws URISyntaxException {
        var secretKey = walletService.add(
                walletRequest.getUsername(),
                walletRequest.getCurrencyName()
        );
        return ResponseEntity
                .created(new URI("/api/v1/wallets/" + secretKey))
                .body(Map.of("secret_key", secretKey));
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getWalletBalance(
            @RequestBody TransactionBalanceRequest transactionBalanceRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        var balance = walletService.getWalletBalance(
                transactionBalanceRequest.getSecretKey(),
                transactionBalanceRequest.getCurrencyName()
        );
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NoAuthHeaderException(HttpStatus.UNAUTHORIZED, "No authentication header in request");
        }
        var jwtToken = authHeader.substring(7);

        var repoUser = userService.getUser(transactionBalanceRequest.getSecretKey()).getUsername();
        var tokenUser = jwtService.extractUsername(jwtToken);

        if (!repoUser.equals(tokenUser)) {
            throw new UnableToAccessToForeignerWalletException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to get foreigner info");
        }

        return ResponseEntity
                .ok()
                .body(Map.of(transactionBalanceRequest.getCurrencyName() + "_wallet", balance));
    }

    @GetMapping("/admin/balances")
    public ResponseEntity<?> getAllWalletsBalance(
            @RequestBody TransactionBalanceRequest transactionBalanceRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NoAuthHeaderException(HttpStatus.UNAUTHORIZED, "No authentication header in request");
        }
        var jwtToken = authHeader.substring(7);

        var repoUser = userService.getUser(transactionBalanceRequest.getSecretKey()).getUsername();
        var tokenUser = jwtService.extractUsername(jwtToken);
        if (!repoUser.equals(tokenUser)) {
            throw new UnableToAccessToForeignerWalletException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to get foreigner account info");
        }

        var namesAndBalances = walletService.getAllWalletsBalance(
                transactionBalanceRequest.getSecretKey()
        );

        return ResponseEntity
                .ok()
                .body(namesAndBalances);
    }

    @PostMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(
            @RequestBody ExchangeRequest exchangeRequest
    ) {
        var currencyNameFrom = exchangeRequest.getCurrencyFrom().getCurrencyName();
        var currencyNameTo = exchangeRequest.getCurrencyTo().getCurrencyName();
        var inputToOtputRelation = exchangeRateService
                .getLatest(currencyNameFrom, currencyNameTo)
                .getCurrencyCoefficient();
        var response = walletService.exchange(
                exchangeRequest.getSecretKey(),
                currencyNameFrom,
                currencyNameTo,
                exchangeRequest.getAmount(),
                inputToOtputRelation
        );
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestBody Map<String, String> depositRequest
    ) {
        var pattern = Pattern.compile(".+_");

        var walletName = "";
        var currencyName = "";
        var secretKey = "";
        double amount;
        for (String s : depositRequest.keySet()) {
            if (s.contains("wallet")) {
                walletName = s;
            }
        }

        var matcher = pattern.matcher(walletName);
        if (matcher.find()) {
            currencyName = walletName.substring(matcher.start(), matcher.end() - 1);
        } else
            throw new ParseJsonException(HttpStatus.BAD_REQUEST, "Could not parse request json");

        try {
            secretKey = depositRequest.get("secret_key");
            amount = Double.parseDouble(depositRequest.get(currencyName + "_wallet"));
        } catch (Exception e) {
            throw new ParseJsonException(HttpStatus.BAD_REQUEST, "Could not parse request json");
        }

        if (currencyName.isEmpty() || secretKey.isEmpty()) {
            throw new ParseJsonException(HttpStatus.BAD_REQUEST, "Could not parse request json");
        } else if (amount < 0) {
            throw new InvalidMoneyAmountException(HttpStatus.BAD_REQUEST, "Negative money amount");
        }
        var response = walletService.changeWalletBalance(
                secretKey,
                currencyName,
                amount
        );

        return ResponseEntity.ok()
                .body(Map.of(currencyName + "_wallet", response));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestBody WithdrawCurrencyRequest withdrawCurrencyRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NoAuthHeaderException(HttpStatus.UNAUTHORIZED, "No authentication header in request");
        }
        var jwtToken = authHeader.substring(7);
        var secretKey = withdrawCurrencyRequest.getSecretKey();
        var repoUser = userService.getUser(secretKey).getUsername();
        var tokenUser = jwtService.extractUsername(jwtToken);
        if (!repoUser.equals(tokenUser)) {
            throw new UnableToAccessToForeignerWalletException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to withdraw money from foreigner wallet");
        }
        var wallet = walletService.findByCurrencyAndKey(
                withdrawCurrencyRequest.getCurrency(),
                secretKey
        );
        var amount = Double.parseDouble(withdrawCurrencyRequest.getCount());
        if (amount < 0) {
            throw new InvalidMoneyAmountException(HttpStatus.BAD_REQUEST, "Negative money amount");
        }

        var response = walletService.changeWalletBalance(
                secretKey,
                withdrawCurrencyRequest.getCurrency(),
                -amount);
        return ResponseEntity.ok(Map.of(wallet.getCurrency().getCurrencyName() + "_wallet", response));
    }


}
