package com.company.cryptoexchangetask.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "exchange_rates", schema = "public")
public class ExchangeRate  implements Serializable {
    public ExchangeRate(Currency inputCurrency, Currency outputCurrency, double currencyСoefficient, LocalDateTime date) {
        this.inputCurrency = inputCurrency;
        this.outputCurrency = outputCurrency;
        this.currencyСoefficient = currencyСoefficient;
        this.date = date;
    }
    public ExchangeRate() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exchange_rate_id")
    private Long id;
    @ManyToMany
    @JoinColumn(name = "currency_id")
    @NotEmpty(message = "Input currency can not be empty")
    private Currency inputCurrency;
    @OneToOne
    @JoinColumn(name = "currency_id")
    @NotEmpty(message = "Output currency can not be empty")
    private Currency outputCurrency;

    @NotEmpty(message = "Currency coefficient can not be empty")
    @Column(name = "currency_coefficient")
    private double currencyСoefficient;

    @Column(name = "date")
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getInputCurrency() {
        return inputCurrency;
    }

    public void setInputCurrency(Currency inputCurrency) {
        this.inputCurrency = inputCurrency;
    }

    public Currency getOutputCurrency() {
        return outputCurrency;
    }

    public void setOutputCurrency(Currency outputCurrency) {
        this.outputCurrency = outputCurrency;
    }

    public double getCurrencyСoefficient() {
        return currencyСoefficient;
    }

    public void setCurrencyСoefficient(double currencyСoefficient) {
        this.currencyСoefficient = currencyСoefficient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
