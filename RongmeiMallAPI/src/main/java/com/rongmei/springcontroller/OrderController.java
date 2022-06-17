package com.rongmei.springcontroller;

import com.rongmei.blservice.order.OrderBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.order.OrderUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.order.OrderDetailResponse;
import com.rongmei.response.order.OrderGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
public class OrderController {

  private final OrderBlService orderBlService;

  @Autowired
  public OrderController(OrderBlService orderBlService) {
    this.orderBlService = orderBlService;
  }

  @ApiOperation(value = "获取组订单信息", notes = "根据组ID获取订单信息")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = OrderGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getOrders(@RequestParam("userGroupId") int userGroupId) {
    return new ResponseEntity<>(orderBlService.getOrders(userGroupId), HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的订单信息", notes = "获取我的订单信息")
  @RequestMapping(value = "mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = OrderGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineOrders(@RequestParam("status") String status,
      @RequestParam(value = "orderType", defaultValue = "group") String orderType) {
    return new ResponseEntity<>(orderBlService.getMineOrders(status, orderType), HttpStatus.OK);
  }

  @ApiOperation(value = "判断订单是否存在", notes = "判断订单是否存在")
  @RequestMapping(value = "existence", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = OrderDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> isOrderExist(@RequestParam("status") String status,
      @RequestParam(value = "orderType", defaultValue = "group") String orderType,
      @RequestParam("relationId") int relationId) {
    try {
      return new ResponseEntity<>(
          orderBlService.isOrderExist(status, orderType, relationId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取某订单信息", notes = "根据订单ID获取订单信息")
  @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = OrderDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getOrder(@PathVariable("orderId") int orderId) {
    try {
      return new ResponseEntity<>(orderBlService.getOrder(orderId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "更新订单信息", notes = "更新订单信息")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = OrderDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateOrder(@RequestBody OrderUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(orderBlService.updateOrder(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
