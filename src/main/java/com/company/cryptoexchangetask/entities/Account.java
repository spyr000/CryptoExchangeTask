package com.company.cryptoexchangetask.entities;

import com.company.cryptoexchangetask.entities.user.User;
import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "accounts", schema = "public")
public class Account implements Serializable {
    public Account(User user, double moneyAmt, Currency currency) {
        this.user = user;
        this.moneyAmt = moneyAmt;
        this.currency = currency;
    }

    public Account() {
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "Account must have an owner user")
    private User user;
    @Id
    private Long id;

    @Column(name = "money_amount")
    private double moneyAmt;

    @OneToOne
    @JoinColumn(name = "currency_id")
    @NotEmpty(message = "Account must have a currency")
    private Currency currency;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMoneyAmt() {
        return moneyAmt;
    }

    public void setMoneyAmt(double moneyAmt) {
        this.moneyAmt = moneyAmt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
