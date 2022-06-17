package com.rongmei.springcontroller;

import com.rongmei.blservice.boxorder.BoxOrderBlService;
import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.boxorder.BoxOrderCommentParameters;
import com.rongmei.parameters.boxorder.BoxOrderRequestSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultCoverUrlSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultUrlSubmitParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.boxorder.BoxOrderDetailResponse;
import com.rongmei.response.boxorder.BoxOrderGetResponse;
import com.rongmei.response.boxorder.BoxOrderRequestResponse;
import com.rongmei.response.boxorder.BoxOrderShareGetResponse;
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
@RequestMapping(value = "box_order")
public class BoxOrderController {

  private final BoxOrderBlService boxOrderBlService;

  @Autowired
  public BoxOrderController(BoxOrderBlService boxOrderBlService) {
    this.boxOrderBlService = boxOrderBlService;
  }

  @ApiOperation(value = "提交拼盒盒申请", notes = "提交拼盒盒申请")
  @RequestMapping(value = "request", method = RequestMethod.PUT)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> submitBoxOrderRequest(
      @RequestBody BoxOrderRequestSubmitParameters parameters) {
    return new ResponseEntity<>(boxOrderBlService.submitBoxOrderRequest(parameters),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的拼盒盒申请", notes = "获取我的拼盒盒申请")
  @RequestMapping(value = "request/query/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BoxOrderRequestResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineBoxOrderRequest(@RequestParam("status") int status,
      @RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        boxOrderBlService.queryUserBoxOrderRequest(status, UserInfoUtil.getUsername(), page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "查询拼盒盒列表", notes = "查询拼盒盒列表")
  @RequestMapping(value = "query", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BoxOrderGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> queryBoxOrder(@RequestParam("status") int status,
      @RequestParam("page") int page, @RequestParam("limit") int limit) {
    return new ResponseEntity<>(boxOrderBlService.queryBoxOrder(status, page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "抢单", notes = "抢单")
  @RequestMapping(value = "grab/{orderId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> grabBoxOrder(@PathVariable("orderId") long orderId) {
    try {
      return new ResponseEntity<>(
          boxOrderBlService.grabBoxOrder(orderId, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "上传成图", notes = "上传成图")
  @RequestMapping(value = "result_cover", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> submitBoxOrderResultCover(
      @RequestBody BoxOrderResultCoverUrlSubmitParameters parameters) {
    try {
      return new ResponseEntity<>(boxOrderBlService.submitBoxOrderResultCover(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "上传成图资源", notes = "上传成图资源")
  @RequestMapping(value = "result", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> submitBoxOrderResult(
      @RequestBody BoxOrderResultUrlSubmitParameters parameters) {
    try {
      return new ResponseEntity<>(boxOrderBlService.submitBoxOrderResult(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "评论", notes = "评论")
  @RequestMapping(value = "comment", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> commentBoxOrder(
      @RequestBody BoxOrderCommentParameters parameters) {
    try {
      return new ResponseEntity<>(boxOrderBlService.commentBoxOrder(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取拼盒盒详情", notes = "获取拼盒盒详情")
  @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BoxOrderDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBoxOrder(@PathVariable("orderId") int orderId) {
    try {
      return new ResponseEntity<>(boxOrderBlService.getBoxOrder(orderId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "拼单", notes = "拼单")
  @RequestMapping(value = "share/{orderId}", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> shareBoxOrder(@PathVariable("orderId") int orderId) {
    try {
      return new ResponseEntity<>(
          boxOrderBlService.shareBoxOrderWithUser(orderId, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (ParametersErrorException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "获取正在拼单", notes = "获取正在拼单")
  @RequestMapping(value = "sharing", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BoxOrderShareGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getSharingBoxOrder() {
    return new ResponseEntity<>(
        boxOrderBlService.getSharingBoxOrderWithoutUser(UserInfoUtil.getUsername()), HttpStatus.OK);
  }
}