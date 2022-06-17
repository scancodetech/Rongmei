package com.rongmei.blservice.order;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.order.OrderUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.order.OrderDetailResponse;
import com.rongmei.response.order.OrderGetResponse;

public interface OrderBlService {

  OrderGetResponse getOrders(int userGroupId);

  OrderGetResponse getMineOrders(String status, String orderType);

  OrderDetailResponse getOrder(int orderId) throws ThingIdDoesNotExistException;

  Response updateOrder(OrderUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  OrderDetailResponse isOrderExist(String status, String orderType, int relationId)
      throws ThingIdDoesNotExistException;
}
