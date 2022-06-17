package com.rongmei.springcontroller;

import com.rongmei.blservice.event.EventBlService;
import com.rongmei.parameters.event.EventApplyParameters;
import com.rongmei.parameters.event.EventApplyUploadParameters;
import com.rongmei.parameters.event.EventUploadParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "event")
public class EventController {

  private final EventBlService eventBlService;

  @Autowired
  public EventController(EventBlService eventBlService) {
    this.eventBlService = eventBlService;
  }

  @ApiOperation(value = "上传活动", notes = "上传活动")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> uploadEvent(
      @RequestBody EventUploadParameters parameter) {
    return new ResponseEntity<>(
        eventBlService.uploadEvent(parameter, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "删除活动", notes = "删除活动")
  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteEvent(@RequestParam("eventId") int eventId) {
    return new ResponseEntity<>(eventBlService.deleteEvent(eventId), HttpStatus.OK);
  }

  @ApiOperation(value = "获取活动列表", notes = "获取活动列表")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getEvents(
      @RequestParam(value = "status", defaultValue = "-1") int status) {
    return new ResponseEntity<>(
        eventBlService.getEvents(status, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "申请参与活动审核", notes = "申请参与活动审核")
  @RequestMapping(value = "apply", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> applyEvent(@RequestBody EventApplyParameters parameter) {
    return new ResponseEntity<>(
        eventBlService.applyEvent(parameter, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "上传活动文件", notes = "上传活动文件")
  @RequestMapping(value = "apply/upload", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> uploadApplyEvent(
      @RequestBody EventApplyUploadParameters parameter) {
    return new ResponseEntity<>(
        eventBlService.uploadApplyEvent(parameter, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

}
