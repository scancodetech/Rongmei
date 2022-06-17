package com.rongmei.springcontroller;

import com.rongmei.blservice.usergroup.UserGroupBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.usergroup.UserGroupUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.order.OrderDetailResponse;
import com.rongmei.response.usergroup.UserGroupDetailResponse;
import com.rongmei.response.usergroup.UserGroupGetResponse;
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
@RequestMapping(value = "user_group")
public class UserGroupController {

  private final UserGroupBlService userGroupBlService;

  @Autowired
  public UserGroupController(UserGroupBlService userGroupBlService) {
    this.userGroupBlService = userGroupBlService;
  }

  @ApiOperation(value = "获取用户组", notes = "获取用户组（在融梅商城里是一个商品）")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserGroupGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getUserGroups(@RequestParam("firstType") String firstType,
      @RequestParam("secondType") String secondType) {
    return new ResponseEntity<>(userGroupBlService.getUserGroups(firstType, secondType),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取某用户组", notes = "根据组ID获取某用户组")
  @RequestMapping(value = "{userGroupId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserGroupDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getUserGroup(@PathVariable("userGroupId") int userGroupId) {
    try {
      return new ResponseEntity<>(userGroupBlService.getUserGroup(userGroupId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "更新用户组", notes = "更新用户组")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserGroupDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateUserGroup(
      @RequestBody UserGroupUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(userGroupBlService.updateUserGroup(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
