package com.rongmei.parameters.blindboxnft;

public class BlindBoxSaleUpdateParameters {

  private int id;
  private int price;
  private int blindBoxNftId;

  public BlindBoxSaleUpdateParameters() {
  }

  public BlindBoxSaleUpdateParameters(int id, int price, int blindBoxNftId) {
    this.id = id;
    this.price = price;
    this.blindBoxNftId = blindBoxNftId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getBlindBoxNftId() {
    return blindBoxNftId;
  }

  public void setBlindBoxNftId(int blindBoxNftId) {
    this.blindBoxNftId = blindBoxNftId;
  }
}
