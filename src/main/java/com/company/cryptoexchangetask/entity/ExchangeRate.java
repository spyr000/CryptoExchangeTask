package com.company.cryptoexchangetask.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exchange_rates", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"input_currency_id", "output_currency_id"})})
public class ExchangeRate implements Serializable {
    @Builder
    public ExchangeRate(Currency inputCurrency, Currency outputCurrency, double currencyCoefficient) {
        this.inputCurrency = inputCurrency;
        this.outputCurrency = outputCurrency;
        this.currencyCoefficient = currencyCoefficient;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exchange_rate_id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "input_currency_id", nullable = false)
    private Currency inputCurrency;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "output_currency_id", nullable = false)
    private Currency outputCurrency;

    @Column(name = "currency_coefficient", nullable = false)
    private double currencyCoefficient;
}
