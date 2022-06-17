package com.rongmei.response.finance;

import java.math.BigDecimal;

public class Finacnce2Mterial extends FinanceF {

    private BigDecimal transactionCash;

    private BigDecimal serviceCash;

    //pc or creator
    private BigDecimal transactionShare;

    public Finacnce2Mterial() {
    }

    public Finacnce2Mterial(Long id, String username, Long createTime, String incomeCash, BigDecimal balance, BigDecimal transactionCash, BigDecimal serviceCash, BigDecimal transactionShare) {
        super(id, username, createTime, incomeCash, balance);
        this.transactionCash = transactionCash;
        this.serviceCash = serviceCash;
        this.transactionShare = transactionShare;
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

    public BigDecimal getTransactionShare() {
        return transactionShare;
    }

    public void setTransactionShare(BigDecimal transactionShare) {
        this.transactionShare = transactionShare;
    }
}
