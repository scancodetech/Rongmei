package com.rongmei.response.finance;

import com.rongmei.entity.finance.Finance;

public class Finacnce2UserAccount {

    private long id;

    private String orderNumber;

    //
    private String payType;

    private String status = "已完成";

    private String name;

    private String monryType;

    private Integer type; //0收入 1支出

    private double count;



    private long time;

    public String getMonryType() {
        return monryType;
    }

    public void setMonryType(String monryType) {
        this.monryType = monryType;
    }

    public Finacnce2UserAccount(String monryType, long id, String orderNumber, String payType, String name, Integer type, double count, long time) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.payType = payType;
        this.name = name;
        this.type = type;
        this.count = count;
        this.time = time;
        this.monryType = monryType;
    }

    public Finacnce2UserAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Finacnce2UserAccount(Finance finance){
        this.id = finance.getId();
        this.orderNumber = finance.getOrderNumber();
        this.payType = finance.getPayType();
        this.time = finance.getCreateTime();
        this.name = finance.getDescs();
        this.monryType = finance.getType();
        double income = finance.getIncomeCash().doubleValue();
        double expenditure = finance.getExpenditureCash().doubleValue();

        if(finance.getTransactionType().equals("收入")){
            this.type = 1;
            this.count = income;
        }else {
            this.type = 0;
            this.count = expenditure;
        }
    }
    }
