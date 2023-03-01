package com.company.cryptoexchangetask.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionCountRequest {
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    LocalDateTime dateFrom;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    LocalDateTime dateTo;
}
