package com.company.cryptoexchangetask.entity;

import com.company.cryptoexchangetask.entity.user.User;
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
@Table(name = "wallets", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "currency_id"})})
public class Wallet implements Serializable {
    @Builder
    public Wallet(User user, double balance, Currency currency) {
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id", nullable = false)
    private Long id;

    @Column(name = "balance")
    private double balance;

    @OneToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}
