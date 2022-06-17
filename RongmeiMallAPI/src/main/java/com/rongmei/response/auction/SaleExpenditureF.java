package com.rongmei.response.auction;

import com.rongmei.response.finance.FinanceF;

public class SaleExpenditureF extends FinanceF {


    /*
    退还保证金
     */
  private double refundDeposit;


  /*
  交易总额 创作者*85
   */
  private double totaltransaction;


  /*
  咕咕费 15
   */

  private double guguMoney;



  public SaleExpenditureF(){}

  public SaleExpenditureF(double refundDeposit, double totaltransaction, double guguMoney){
    this.guguMoney =guguMoney;
    this.refundDeposit = refundDeposit;
    this.totaltransaction = totaltransaction;
  }




  public double getRefundDeposit() {
    return refundDeposit;
  }

  public void setRefundDeposit(double refundDeposit) {
    this.refundDeposit = refundDeposit;
  }

  public double getTotaltransaction() {
    return totaltransaction;
  }

  public void setTotaltransaction(double totaltransaction) {
    this.totaltransaction = totaltransaction;
  }

  public double getGuguMoney() {
    return guguMoney;
  }

  public void setGuguMoney(double guguMoney) {
    this.guguMoney = guguMoney;
  }
}
