package com.company.cryptoexchangetask.entities;

import com.company.cryptoexchangetask.entities.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts", schema = "public")
public class Account implements Serializable {
    @Builder
    public Account(User user, double moneyAmt, Currency currency) {
        this.user = user;
        this.moneyAmt = moneyAmt;
        this.currency = currency;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "money_amount")
    private double moneyAmt;

    @OneToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}
