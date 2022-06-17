package com.rongmei.springcontroller;

import com.rongmei.blservice.child.SimpleChildBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.SimpleChildUpdateParameters;
import com.rongmei.parameters.child.TopicUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.response.child.SimpleChildGetResponse;
import com.rongmei.response.child.TopicDetailResponse;
import com.rongmei.response.child.TopicsGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "simple_child")
public class SimpleChildController {

  private final SimpleChildBlService simpleChildBlService;

  @Autowired
  public SimpleChildController(
      SimpleChildBlService simpleChildBlService) {
    this.simpleChildBlService = simpleChildBlService;
  }

  @ApiOperation(value = "更新遛孩子", notes = "更新遛孩子")
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateChild(@RequestBody SimpleChildUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(simpleChildBlService.updateChild(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "删除遛孩子", notes = "删除遛孩子")
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteChild(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(simpleChildBlService.deleteChild(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取我的遛孩子列表", notes = "获取我的遛孩子列表")
  @RequestMapping(value = "mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SimpleChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineChild(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        simpleChildBlService.getUserChild(page, limit, UserInfoUtil.getUsername()), HttpStatus.OK);
  }

  @ApiOperation(value = "获取遛孩子列表", notes = "获取遛孩子列表")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getChildren(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(simpleChildBlService.getChildren(page, limit), HttpStatus.OK);
  }

  @ApiOperation(value = "获取话题遛孩子列表", notes = "根据话题，获取遛孩子列表")
  @RequestMapping(value = "topic", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getChildrenByTopic(@RequestParam("topic") String topic,
      @RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(simpleChildBlService.getChildrenByTopic(topic, page, limit),
        HttpStatus.OK);
  }

  @PreAuthorize("hasAnyAuthority('THEME','ROLE_USER')")
  @ApiOperation(value = "获取热门话题", notes = "获取热门话题")
  @RequestMapping(value = "topic/top", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TopicsGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopTopics(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(simpleChildBlService.getTopTopics(page, limit), HttpStatus.OK);
  }

  @PreAuthorize("hasAnyAuthority('THEME','ROLE_USER')")
  @ApiOperation(value = "获取话题详情", notes = "获取话题详情")
  @RequestMapping(value = "topic/detail", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TopicDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopicDetail(@RequestParam("topic") String topic) {
    return new ResponseEntity<>(simpleChildBlService.getTopicDetail(topic), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('THEME')")
  @ApiOperation(value = "更新话题", notes = "更新话题资料")
  @RequestMapping(value = "topic", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateTopic(@RequestBody TopicUpdateParameters parameters) {
    return new ResponseEntity<>(simpleChildBlService.updateTopic(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "获取用户溜孩子", notes = "获取用户溜孩子")
  @RequestMapping(value = "user", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getUserChild(@RequestParam("username") String username,
      @RequestParam("page") int page, @RequestParam("limit") int limit) {
    return new ResponseEntity<>(simpleChildBlService.getUserChild(page, limit, username),
        HttpStatus.OK);
  }
}
