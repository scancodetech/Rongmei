package com.rongmei.response.finance;

import java.math.BigDecimal;

public class Finacnce2Box extends FinanceF {

    private BigDecimal transactionCash;

    private BigDecimal serviceCash;

    public Finacnce2Box(){
    }

    public Finacnce2Box(Long id, String username, Long createTime, String incomeCash, BigDecimal balance, BigDecimal transactionCash, BigDecimal serviceCash) {
        super(id, username, createTime, incomeCash, balance);
        this.transactionCash = transactionCash;
        this.serviceCash = serviceCash;
    }

    public BigDecimal getTransactionCash() {
        return transactionCash;
    }

    public void setTransactionCash(BigDecimal transactionCash) {
        this.transactionCash = transactionCash;
    }

    public BigDecimal getServiceCash() {
        return serviceCash;
    }

    public void setServiceCash(BigDecimal serviceCash) {
        this.serviceCash = serviceCash;
    }
}
