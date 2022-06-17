package com.rongmei.parameters.blindboxnft;

public class BlindBoxOrderCancelParameter {

  private int orderId;

  public BlindBoxOrderCancelParameter() {
  }

  public BlindBoxOrderCancelParameter(int orderId) {
    this.orderId = orderId;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }
}
