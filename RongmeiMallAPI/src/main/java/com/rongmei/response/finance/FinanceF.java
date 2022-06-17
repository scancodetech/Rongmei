package com.rongmei.response.finance;

import java.math.BigDecimal;

public class FinanceF {
    private Long id;

    private String username;

    private Long createTime;

    private String incomeCash;

    private BigDecimal balance;


    public FinanceF() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomeCash() {
        return incomeCash;
    }

    public void setIncomeCash(String incomeCash) {
        this.incomeCash = incomeCash;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public FinanceF(Long id, String username, Long createTime, String incomeCash, BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.createTime = createTime;
        this.incomeCash = incomeCash;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
