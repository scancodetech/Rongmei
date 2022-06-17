package com.rongmei.parameters.boxorder;

public class BoxOrderRequestSubmitParameters {

  private int id;
  private int orderType;

  private String cooperateType;
  private String customType;
  private String customStyle;
  private String worldOutlook;
  private String customTheme;
  private String elementRequirements;
  private String exampleUrl;
  private int price;

  public BoxOrderRequestSubmitParameters() {
  }


  public BoxOrderRequestSubmitParameters(int id, int orderType, String cooperateType,
      String customType, String customStyle, String worldOutlook, String customTheme,
      String elementRequirements, String exampleUrl, int price) {
    this.id = id;
    this.orderType = orderType;
    this.cooperateType = cooperateType;
    this.customType = customType;
    this.customStyle = customStyle;
    this.worldOutlook = worldOutlook;
    this.customTheme = customTheme;
    this.elementRequirements = elementRequirements;
    this.exampleUrl = exampleUrl;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCooperateType() {
    return cooperateType;
  }

  public void setCooperateType(String cooperateType) {
    this.cooperateType = cooperateType;
  }

  public String getWorldOutlook() {
    return worldOutlook;
  }

  public void setWorldOutlook(String worldOutlook) {
    this.worldOutlook = worldOutlook;
  }

  public int getOrderType() {
    return orderType;
  }

  public void setOrderType(int orderType) {
    this.orderType = orderType;
  }

  public String getCustomType() {
    return customType;
  }

  public void setCustomType(String customType) {
    this.customType = customType;
  }

  public String getCustomStyle() {
    return customStyle;
  }

  public void setCustomStyle(String customStyle) {
    this.customStyle = customStyle;
  }

  public String getCustomTheme() {
    return customTheme;
  }

  public void setCustomTheme(String customTheme) {
    this.customTheme = customTheme;
  }

  public String getElementRequirements() {
    return elementRequirements;
  }

  public void setElementRequirements(String elementRequirements) {
    this.elementRequirements = elementRequirements;
  }

  public String getExampleUrl() {
    return exampleUrl;
  }

  public void setExampleUrl(String exampleUrl) {
    this.exampleUrl = exampleUrl;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
