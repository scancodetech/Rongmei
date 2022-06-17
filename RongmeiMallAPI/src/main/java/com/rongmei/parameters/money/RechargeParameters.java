package com.rongmei.parameters.money;

public class RechargeParameters {

  private String subject;
  private String totalAmount;

  public RechargeParameters() {
  }

  public RechargeParameters(String subject, String totalAmount) {
    this.subject = subject;
    this.totalAmount = totalAmount;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }
}
