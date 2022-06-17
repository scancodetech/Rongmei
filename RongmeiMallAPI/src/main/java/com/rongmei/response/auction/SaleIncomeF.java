package com.rongmei.response.auction;


import com.rongmei.response.finance.FinanceF;

public class SaleIncomeF extends FinanceF {


    //起牌价
    private double startPrice;
    //最终
    private int interceptPrice;

    //保证金
    private double earnestMoney;

    //咕咕
    private double cooMoney;

    //版权费
    private double copyrightMoney;

    //服务费
    private double serviceMoney;
    //回收费
    private double backMoney;




    public SaleIncomeF(double startPrice, int interceptPrice, double earnestMoney, double cooMoney, double copyrightMoney, double serviceMoney, double backMoney) {
        this.startPrice = startPrice;
        this.interceptPrice = interceptPrice;
        this.earnestMoney = earnestMoney;
        this.cooMoney = cooMoney;
        this.copyrightMoney = copyrightMoney;
        this.serviceMoney = serviceMoney;
        this.backMoney = backMoney;

    }

    public SaleIncomeF() {
    }




    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public int getInterceptPrice() {
        return interceptPrice;
    }

    public void setInterceptPrice(int interceptPrice) {
        this.interceptPrice = interceptPrice;
    }

    public double getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(double earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public double getCooMoney() {
        return cooMoney;
    }

    public void setCooMoney(double cooMoney) {
        this.cooMoney = cooMoney;
    }

    public double getCopyrightMoney() {
        return copyrightMoney;
    }

    public void setCopyrightMoney(double copyrightMoney) {
        this.copyrightMoney = copyrightMoney;
    }

    public double getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(double serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(double backMoney) {
        this.backMoney = backMoney;
    }
}
