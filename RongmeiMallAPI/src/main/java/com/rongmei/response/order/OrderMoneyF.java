package com.rongmei.response.order;

import com.rongmei.response.finance.FinanceF;

public class OrderMoneyF extends FinanceF {

    //总额
    private double interceptPrice;
    //服务费
    private double servicePrice;
    //分成
    private double dividedPrice;

    public OrderMoneyF() {
    }

    public OrderMoneyF(Integer id, String userName, Long starttime, double income, double allIncome, double interceptPrice, double servicePrice, double dividedPrice) {
        this.interceptPrice = interceptPrice;
        this.servicePrice = servicePrice;
        this.dividedPrice = dividedPrice;
    }


    public double getInterceptPrice() {
        return interceptPrice;
    }

    public void setInterceptPrice(double interceptPrice) {
        this.interceptPrice = interceptPrice;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public double getDividedPrice() {
        return dividedPrice;
    }

    public void setDividedPrice(double dividedPrice) {
        this.dividedPrice = dividedPrice;
    }
}
