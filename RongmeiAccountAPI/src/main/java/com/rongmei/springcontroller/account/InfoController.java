package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.user.UserRoleResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class InfoController {

  private final UserBlService userBlService;

  @Autowired
  public InfoController(UserBlService userBlService) {
    this.userBlService = userBlService;
  }

  @ApiOperation(value = "用户角色", notes = "获得用户角色")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
      @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
  })
  @RequestMapping(value = "role", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserRoleResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getRole() {
    UserRoleResponse userRoleResponse = userBlService.getRole(UserInfoUtil.getUsername());
    return new ResponseEntity<>(userRoleResponse, HttpStatus.OK);
  }

}
