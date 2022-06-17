package com.rongmei.parameters.relation;

public class FavoritesUpdateParameters {
  private int id;
  private String name;
  private int orderId;

  public FavoritesUpdateParameters() {
  }

  public FavoritesUpdateParameters(int id, String name, int orderId) {
    this.id = id;
    this.name = name;
    this.orderId = orderId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }
}
