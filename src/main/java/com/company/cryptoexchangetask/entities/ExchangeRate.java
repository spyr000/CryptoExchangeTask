package com.company.cryptoexchangetask.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exchange_rates", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = { "input_currency", "output_currency" }) })
public class ExchangeRate  implements Serializable {
    @Builder
    public ExchangeRate(Currency inputCurrency, Currency outputCurrency, double currencyCoefficient, LocalDateTime date) {
        this.inputCurrency = inputCurrency;
        this.outputCurrency = outputCurrency;
        this.currencyCoefficient = currencyCoefficient;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exchange_rate_id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "input_currency_id", nullable = false)
    private Currency inputCurrency;

    @OneToOne
    @JoinColumn(name = "output_currency_id", nullable = false)
    private Currency outputCurrency;

    @Column(name = "currency_coefficient", nullable = false)
    private double currencyCoefficient;

    @Column(name = "date")
    private LocalDateTime date;
}
