package com.rongmei.response.order;

import com.rongmei.response.Response;
import java.util.List;

public class OrderGetResponse extends Response {

  private List<OrderItem> orderItems;

  public OrderGetResponse() {
  }

  public OrderGetResponse(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
