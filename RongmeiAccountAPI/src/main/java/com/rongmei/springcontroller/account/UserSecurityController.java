package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserSecurityBlService;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.user.UserSecurityUpdateInnerParameters;
import com.rongmei.parameters.user.UserSecurityUpdateParameters;
import com.rongmei.response.Response;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "user_security")
public class UserSecurityController {

  private final UserSecurityBlService userSecurityBlService;

  @Autowired
  public UserSecurityController(
      UserSecurityBlService userSecurityBlService) {
    this.userSecurityBlService = userSecurityBlService;
  }

  @ApiOperation(value = "获得用户账户基础信息", notes = "获得用户账户支付信息")
  @RequestMapping(method = RequestMethod.GET, path = "basis", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserBasisSecurity() {
    try {
      return new ResponseEntity<>(userSecurityBlService.getUserBasisSecurity(), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获得用户账户高级信息", notes = "获得用户账户支付信息")
  @RequestMapping(method = RequestMethod.GET, path = "advanced/captcha", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserAdvancedSecurityByCaptcha(
      @RequestParam("captcha") String captcha) {
    try {
      return new ResponseEntity<>(userSecurityBlService.getUserAdvancedSecurityByCaptcha(captcha),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "获得用户账户高级信息", notes = "获得用户账户支付信息")
  @RequestMapping(method = RequestMethod.GET, path = "advanced/payCode", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserAdvancedSecurityByPayCode(
      @RequestParam("payCode") String payCode) {
    try {
      return new ResponseEntity<>(userSecurityBlService.getUserAdvancedSecurityByPayCode(payCode),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "设置用户账户", notes = "设置用户账户")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserSecurity(
      @RequestBody UserSecurityUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          userSecurityBlService.updateUserSecurity(parameters), HttpStatus.OK);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "内部接口-设置用户账户", notes = "内部接口-设置用户账户")
  @RequestMapping(method = RequestMethod.POST, path = "inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserSecurityInner(
      @RequestBody UserSecurityUpdateInnerParameters parameters) {
    try {
      return new ResponseEntity<>(
          userSecurityBlService.updateUserSecurityInner(parameters), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "内部接口-获得用户账户高级信息", notes = "内部接口-获得用户账户支付信息")
  @RequestMapping(method = RequestMethod.GET, path = "inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserSecurity(@RequestParam("username") String username) {
    try {
      return new ResponseEntity<>(userSecurityBlService.getUserSecurity(username), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
