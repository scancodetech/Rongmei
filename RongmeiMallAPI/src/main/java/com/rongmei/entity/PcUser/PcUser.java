package com.rongmei.entity.PcUser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "pc_user")
@Scope
@Component
public class PcUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "balance", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal balance;
    @Column(name = "expenditure", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal expenditure;
    @Column(name = "income", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal income;

    @Column(name = "username", columnDefinition = "varchar(255) ")
    private String username;

    public PcUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PcUser(Integer id, BigDecimal balance, BigDecimal expenditure, BigDecimal income, String username) {
        this.id = id;
        this.balance = balance;
        this.expenditure = expenditure;
        this.income = income;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
