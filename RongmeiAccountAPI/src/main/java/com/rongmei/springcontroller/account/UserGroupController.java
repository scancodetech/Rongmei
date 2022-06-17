package com.rongmei.springcontroller.account;

import com.rongmei.blservice.account.UserGroupBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.user.UserGroupApplicationApproveParameters;
import com.rongmei.parameters.user.UserGroupApplyParameters;
import com.rongmei.parameters.user.UserGroupUpdateParameters;
import com.rongmei.response.Response;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "user_group")
public class UserGroupController {

  private final UserGroupBlService userGroupBlService;

  @Autowired
  public UserGroupController(UserGroupBlService userGroupBlService) {
    this.userGroupBlService = userGroupBlService;
  }

  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "获取用户组", notes = "获取用户组")
  @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserGroups() {
    return new ResponseEntity<>(userGroupBlService.getUserGroups(), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "删除用户组", notes = "删除用户组")
  @RequestMapping(method = RequestMethod.DELETE, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> deleteUserGroup(@RequestParam("userGroupId") int userGroupId) {
    return new ResponseEntity<>(userGroupBlService.deleteUserGroup(userGroupId), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "修改用户组", notes = "修改用户组")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
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
  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "获取某个用户组", notes = "获取某个用户组")
  @RequestMapping(method = RequestMethod.GET, path = "{userGroupId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserGroup(@PathVariable("userGroupId") int userGroupId) {
    try {
      return new ResponseEntity<>(userGroupBlService.getUserGroup(userGroupId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "修改用户组余额", notes = "修改用户组余额")
  @RequestMapping(method = RequestMethod.GET, path = "price", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserGroupPrice(@RequestParam("userGroupId") int userGroupId,
      @RequestParam("price") long price) {
    try {
      return new ResponseEntity<>(userGroupBlService.updateUserGroupPrice(userGroupId, price),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
//  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "获得用户所在组", notes = "获得用户所在组")
  @RequestMapping(method = RequestMethod.GET, path = "username", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserGroupByUsername(
      @RequestParam("username") String username) {
    return new ResponseEntity<>(userGroupBlService.getUserGroupByUsername(username),
        HttpStatus.OK);
  }
//  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "申请入驻", notes = "申请入驻")
  @RequestMapping(method = RequestMethod.POST, path = "application", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> applyUserGroup(@RequestBody UserGroupApplyParameters parameters) {
    try {
      return new ResponseEntity<>(userGroupBlService.applyUserGroup(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
//  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "获取入驻信息", notes = "获取入驻信息")
  @RequestMapping(method = RequestMethod.GET, path = "application", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserGroupApplication() {
    try {
      return new ResponseEntity<>(userGroupBlService.getUserGroupApplication(), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "审批入驻信息", notes = "审批入驻信息")
  @RequestMapping(method = RequestMethod.POST, path = "application/approval", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> approveUserGroupApplication(
      @RequestBody UserGroupApplicationApproveParameters parameters) {
    try {
      return new ResponseEntity<>(userGroupBlService.approveUserGroupApplication(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
//  @PreAuthorize("hasAuthority('GROUP')")
  @ApiOperation(value = "获取入驻申请列表", notes = "获取入驻申请列表")
  @RequestMapping(method = RequestMethod.GET, path = "application/admin", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getAllApproveUserGroupApplications() {
    return new ResponseEntity<>(userGroupBlService.getAllApproveUserGroupApplications(),
        HttpStatus.OK);
  }
}
