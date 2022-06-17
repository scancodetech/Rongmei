package com.rongmei.parameters.auction;

public class AuctionParamaters {

  private double price;
  private int saleId;

  public AuctionParamaters() {
  }

  public AuctionParamaters(double price, int saleId) {
    this.price = price;
    this.saleId = saleId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getSaleId() {
    return saleId;
  }

  public void setSaleId(int saleId) {
    this.saleId = saleId;
  }
}
