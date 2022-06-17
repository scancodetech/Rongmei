package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.user.UserAccountUpdateParameters;
import com.rongmei.response.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user_account")
public class AccountController {

  private final UserBlService userBlService;

  @Autowired
  public AccountController(UserBlService userBlService) {
    this.userBlService = userBlService;
  }

  @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息")
  @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserAccount() {
    try {
      return new ResponseEntity<>(userBlService.getUserAccount(), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "设置用户账户", notes = "设置用户账户")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserAccount(
      @RequestBody UserAccountUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          userBlService.updateUserAccount(parameters), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

}
