package com.rongmei.parameters.blindboxnft;

public class BlindBoxOrderFinishParameter {

  private String theme;
  private int blindBoxSaleId;

  public BlindBoxOrderFinishParameter() {
  }

  public BlindBoxOrderFinishParameter(String theme, int blindBoxSaleId) {
    this.theme = theme;
    this.blindBoxSaleId = blindBoxSaleId;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public int getBlindBoxSaleId() {
    return blindBoxSaleId;
  }

  public void setBlindBoxSaleId(int blindBoxSaleId) {
    this.blindBoxSaleId = blindBoxSaleId;
  }
}
