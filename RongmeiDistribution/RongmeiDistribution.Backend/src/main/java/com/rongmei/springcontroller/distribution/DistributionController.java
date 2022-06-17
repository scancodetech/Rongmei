package com.rongmei.springcontroller.distribution;

import com.rongmei.blservice.distribution.DistributionBlService;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.paramaters.distribution.DistributionParameters;
import com.rongmei.paramaters.distribution.UserDistributionUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "distribution")
public class DistributionController {

  private final DistributionBlService distributionBlService;

  @Autowired
  public DistributionController(DistributionBlService distributionBlService) {
    this.distributionBlService = distributionBlService;
  }

  @ApiOperation(value = "获得用户分销信息", notes = "获得用户分销信息")
  @RequestMapping(method = RequestMethod.GET, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserDistribution() {
    try {
      return new ResponseEntity<>(
          distributionBlService.getUserDistribution(UserInfoUtil.getUsername()), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获得用户分销信息", notes = "获得用户分销信息")
  @RequestMapping(method = RequestMethod.GET, path = "user/{username}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserDistribution(@PathVariable("username") String username) {
    try {
      return new ResponseEntity<>(distributionBlService.getUserDistribution(username),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "分销", notes = "分销")
  @RequestMapping(method = RequestMethod.POST, path = "share", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> distribution(@RequestBody DistributionParameters parameters) {
    try {
      return new ResponseEntity<>(distributionBlService.distribution(parameters), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "分销", notes = "分销")
  @RequestMapping(method = RequestMethod.GET, path = "share", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> distribution(@RequestParam("code") String code) {
    try {
      return new ResponseEntity<>(distributionBlService
          .distribution(new DistributionParameters(code, UserInfoUtil.getUsername())),
          HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "修改等级", notes = "修改用户分销等级")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserDistribution(
      @RequestBody UserDistributionUpdateParameters parameters) {
    return new ResponseEntity<>(distributionBlService.updateUserDistribution(parameters),
        HttpStatus.OK);
  }
}
