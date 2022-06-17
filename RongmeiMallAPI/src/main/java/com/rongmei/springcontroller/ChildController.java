package com.rongmei.springcontroller;

import com.rongmei.blservice.child.ChildBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.ChildUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.child.ChildDetailResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.response.child.TopicsGetResponse;
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
@RequestMapping(value = "child")
public class ChildController {

  private final ChildBlService childBlService;

  @Autowired
  public ChildController(ChildBlService childBlService) {
    this.childBlService = childBlService;
  }

  @ApiOperation(value = "更新遛孩子", notes = "更新遛孩子")
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateChild(@RequestBody ChildUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(childBlService.updateChild(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取遛孩子详情", notes = "获取遛孩子详情")
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getChildDetail(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(childBlService.getChildDetail(id), HttpStatus.OK);
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
      return new ResponseEntity<>(childBlService.deleteChild(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取我的遛孩子列表", notes = "获取我的遛孩子列表")
  @RequestMapping(value = "mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineChild(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        childBlService.getUserChild(page, limit, UserInfoUtil.getUsername()), HttpStatus.OK);
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
    return new ResponseEntity<>(childBlService.getChildren(page, limit), HttpStatus.OK);
  }
}
