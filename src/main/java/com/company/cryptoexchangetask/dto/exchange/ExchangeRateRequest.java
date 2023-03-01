package com.company.cryptoexchangetask.dto.exchange;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeRateRequest {
    private String inputCurrencyName;
    private String outputCurrencyName;
    private double outputCost;
}
