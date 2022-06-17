package com.rongmei.springcontroller;

import com.rongmei.blservice.notice.NoticeBlService;
import com.rongmei.parameters.notice.NoticeSendParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.notice.NoticeGetResponse;
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
@RequestMapping(value = "notice")
public class NoticeController {

  private final NoticeBlService noticeBlService;

  @Autowired
  public NoticeController(NoticeBlService noticeBlService) {
    this.noticeBlService = noticeBlService;
  }

  @ApiOperation(value = "发送站内信", notes = "发送站内信")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> sendNotice(@RequestBody NoticeSendParameters parameters) {
    return new ResponseEntity<>(noticeBlService.sendNotice(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的站内信", notes = "获取我的站内信")
  @RequestMapping(value = "mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = NoticeGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineNotice(@RequestParam("type") int type) {
    return new ResponseEntity<>(noticeBlService.getMineNotice(type), HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的所有站内信", notes = "获取我的所有站内信")
  @RequestMapping(value = "mine/all", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = NoticeGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineAllNotice(@RequestParam(name = "page") int page,@RequestParam(name = "limit")int limit) {
    return new ResponseEntity<>(noticeBlService.getMineAllNotice(page,limit), HttpStatus.OK);
  }
}
