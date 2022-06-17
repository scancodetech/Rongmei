package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserRelationBlService;
import com.rongmei.parameters.user.UserRelationUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.user.UserRelationResponse;
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
@RequestMapping(value = "user_relation")
public class UserRelationController {

  private final UserRelationBlService userRelationBlService;

  @Autowired
  public UserRelationController(
      UserRelationBlService userRelationBlService) {
    this.userRelationBlService = userRelationBlService;
  }

  @ApiOperation(value = "获得用户关联信息", notes = "获得用户关联信息")
  @RequestMapping(method = RequestMethod.GET, path = "mine", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getMineUserRelation() {
    return new ResponseEntity<>(
        userRelationBlService.getUserRelation(UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获得用户关联信息", notes = "获得用户关联信息")
  @RequestMapping(method = RequestMethod.GET, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserRelation(@RequestParam("username") String username) {
    return new ResponseEntity<>(
        userRelationBlService.getUserRelation(username),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获得用户关联信息", notes = "获得用户关联信息")
  @RequestMapping(value = "like", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserRelationResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getUserRelationLike(@RequestParam("toUsername") String toUsername,
      @RequestParam("type") int type) {
    return new ResponseEntity<>(
        userRelationBlService.getUserRelationLike(UserInfoUtil.getUsername(), toUsername, type),
        HttpStatus.OK);
  }

  @ApiOperation(value = "更新用户关联信息", notes = "更新用户关联信息")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserRelation(
      @RequestBody UserRelationUpdateParameters parameters) {
    return new ResponseEntity<>(userRelationBlService.updateUserRelation(parameters),
        HttpStatus.OK);
  }
}
