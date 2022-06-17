package com.rongmei.response.finance;

import java.math.BigDecimal;

public class Finacnce2Sale extends FinanceF{

    private BigDecimal startPrice;

    private BigDecimal transactionShare;



    private BigDecimal margin;

    private BigDecimal copyrightCash;

    private BigDecimal guguShare;

    private BigDecimal serviceCash;

    private BigDecimal cancelCash;

    public BigDecimal getTransactionCash() {
        return transactionCash;
    }

    public void setTransactionCash(BigDecimal transactionCash) {
        this.transactionCash = transactionCash;
    }

    private BigDecimal transactionCash;


    public Finacnce2Sale() {
    }

    public Finacnce2Sale(Long id, String username, Long createTime, String incomeCash, BigDecimal balance, BigDecimal startPrice, BigDecimal transactionShare, BigDecimal margin, BigDecimal copyrightCash, BigDecimal guguShare, BigDecimal serviceCash, BigDecimal cancelCash,BigDecimal transactionCash) {
        super(id, username, createTime, incomeCash, balance);
        this.startPrice = startPrice;
        this.transactionShare = transactionCash;
        this.margin = margin;
        this.copyrightCash = copyrightCash;
        this.guguShare = guguShare;
        this.serviceCash = serviceCash;
        this.cancelCash = cancelCash;
        this.transactionCash = transactionCash;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getTransactionShare() {
        return transactionShare;
    }

    public void setTransactionShare(BigDecimal transactionCash) {
        this.transactionShare = transactionCash;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getCopyrightCash() {
        return copyrightCash;
    }

    public void setCopyrightCash(BigDecimal copyrightCash) {
        this.copyrightCash = copyrightCash;
    }

    public BigDecimal getGuguShare() {
        return guguShare;
    }

    public void setGuguShare(BigDecimal guguShare) {
        this.guguShare = guguShare;
    }

    public BigDecimal getServiceCash() {
        return serviceCash;
    }

    public void setServiceCash(BigDecimal serviceCash) {
        this.serviceCash = serviceCash;
    }

    public BigDecimal getCancelCash() {
        return cancelCash;
    }

    public void setCancelCash(BigDecimal cancelCash) {
        this.cancelCash = cancelCash;
    }
}
