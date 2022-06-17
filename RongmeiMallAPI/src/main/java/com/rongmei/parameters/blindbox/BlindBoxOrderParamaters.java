package com.rongmei.parameters.blindbox;

public class BlindBoxOrderParamaters {

  private long id;
  private long blindBoxId;
  private int price;
  private String theme;
  private String classification;

  public BlindBoxOrderParamaters() {
  }

  public BlindBoxOrderParamaters(long id, long blindBoxId, int price, String theme,
      String classification) {
    this.id = id;
    this.blindBoxId = blindBoxId;
    this.price = price;
    this.theme = theme;
    this.classification = classification;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getBlindBoxId() {
    return blindBoxId;
  }

  public void setBlindBoxId(long blindBoxId) {
    this.blindBoxId = blindBoxId;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }
}
