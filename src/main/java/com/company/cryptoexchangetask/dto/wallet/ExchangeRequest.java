package com.company.cryptoexchangetask.dto.wallet;

import com.company.cryptoexchangetask.entity.Currency;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeRequest {
    private String secretKey;
    private Currency currencyFrom;
    private Currency currencyTo;
    private double amount;
}
