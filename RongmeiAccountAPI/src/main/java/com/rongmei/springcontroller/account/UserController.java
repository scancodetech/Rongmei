package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.user.RegisterParameters;
import com.rongmei.parameters.user.UserAccountUpdateParameters;
import com.rongmei.parameters.user.UserInfoSaveParameters;
import com.rongmei.parameters.user.UserPasswordUpdateParamaters;
import com.rongmei.parameters.user.UserRoleUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.employees.EmployeesLoginResponse;
import com.rongmei.response.user.UserLoginResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "account")
public class UserController {

  private final UserBlService userBlService;

  @Autowired
  public UserController(UserBlService userBlService) {
    this.userBlService = userBlService;
  }


  @ApiOperation(value = "发送验证码", notes = "发送验证码")
  @RequestMapping(method = RequestMethod.GET, path = "captcha", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> sendCaptcha(@RequestParam("phone") String phone) {
    try {
      return new ResponseEntity<>(userBlService.sendCaptcha(phone), HttpStatus.OK);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "用户登录", notes = "验证用户登录并返回token")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
      @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
  })
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> login(
      @RequestParam("phone") String phone, @RequestParam("captcha") String captcha,
      @RequestParam(value = "platformKey", defaultValue = "000000") String platformKey) {
    try {
      UserLoginResponse userLoginResponse = userBlService.login(phone, captcha, platformKey);
      return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
    }
  }
  @ApiOperation(value = "员工用户登录", notes = "验证用户登录并返回token")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
          @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
  })
  @RequestMapping(value = "password", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> loginWithPassword(
          @RequestParam("username") String username, @RequestParam("password") String password,
          @RequestParam(value = "platformKey", defaultValue = "000000") String platformKey) {
    try {
      EmployeesLoginResponse employeesLoginResponse = userBlService
              .loginWithPassword(username, password, platformKey);
      return new ResponseEntity<>(employeesLoginResponse, HttpStatus.OK);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "修改密码", notes = "修改密码")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
      @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
  })
  @RequestMapping(value = "password", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updatePassword(
      @RequestBody UserPasswordUpdateParamaters parameters) {
    try {
      return new ResponseEntity<>(userBlService.updatePassword(parameters), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "用户注册", notes = "用户注册并返回token")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
      @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
  })
  @RequestMapping(value = "register", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = UserLoginResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> register(@RequestBody RegisterParameters parameters) {
    try {
      UserLoginResponse userLoginResponse = userBlService.register(parameters);
      return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "保存用户个人信息", notes = "保存用户个人信息")
  @RequestMapping(method = RequestMethod.POST, path = "info", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> saveUserInfo(
      @RequestBody UserInfoSaveParameters userInfoSaveParameters) {
    try {
      return new ResponseEntity<>(
          userBlService.saveUserInfo(userInfoSaveParameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "设置用户角色", notes = "设置用户角色")
  @RequestMapping(method = RequestMethod.POST, path = "role", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserRole(@RequestBody UserRoleUpdateParameters parameters) {
    return new ResponseEntity<>(
        userBlService.updateUserRole(parameters.getRole(), parameters.getId()), HttpStatus.OK);
  }


  @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
  @RequestMapping(method = RequestMethod.GET, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUsers() {
    return new ResponseEntity<>(userBlService.getUsers(), HttpStatus.OK);
  }

  @ApiOperation(value = "获得用户个人信息", notes = "获得用户个人信息")
  @RequestMapping(method = RequestMethod.GET, path = "info", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserInfo() {
    try {
      return new ResponseEntity<>(userBlService.getUserInfo(UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获得用户个人信息", notes = "获得用户基础个人信息")
  @RequestMapping(method = RequestMethod.GET, path = "user/base", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUser() {
    try {
      return new ResponseEntity<>(userBlService.getUser(UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAnyAuthority('MATERIALSOURCE','ROLE_USER')")
  @ApiOperation(value = "获得用户个人信息", notes = "获得用户个人信息")
  @RequestMapping(method = RequestMethod.GET, path = "info/entity", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserInfo(@RequestParam("username") String username) {
    try {
      return new ResponseEntity<>(userBlService.getUserInfo(username), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息")
  @RequestMapping(method = RequestMethod.GET, path = "account/entity", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserAccount(@RequestParam("username") String username) {
    try {
      return new ResponseEntity<>(userBlService.getUserAccount(username), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获得用户实体类", notes = "获得用户实体类")
  @RequestMapping(method = RequestMethod.GET, path = "entity", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUser(@RequestParam(name = "username") String username) {
    try {
      return new ResponseEntity<>(userBlService.getUser(username), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

}
