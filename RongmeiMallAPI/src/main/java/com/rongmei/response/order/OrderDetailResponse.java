package com.rongmei.response.order;

import com.rongmei.response.Response;

public class OrderDetailResponse extends Response {

  private OrderItem orderItem;

  public OrderDetailResponse() {
  }

  public OrderDetailResponse(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }
}
