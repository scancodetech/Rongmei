package com.rongmei.springcontroller;

import com.rongmei.blservice.groupshopping.GroupShoppingBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.groupshopping.GroupShoppingUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.groupshopping.GroupShoppingDetailResponse;
import com.rongmei.response.groupshopping.GroupShoppingGetResponse;
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
@RequestMapping(value = "group_shopping")
public class GroupShoppingController {

  private final GroupShoppingBlService groupShoppingBlService;

  @Autowired
  public GroupShoppingController(
      GroupShoppingBlService groupShoppingBlService) {
    this.groupShoppingBlService = groupShoppingBlService;
  }

  @ApiOperation(value = "获取推荐页巨人车列表", notes = "根据分页获取推荐页巨人车列表")
  @RequestMapping(value = "recommend", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getRecommendGroupShoppings(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(groupShoppingBlService.getRecommendGroupShoppings(page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取巨人车列表", notes = "根据分页和草稿状态获取巨人车列表")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getGroupShoppings(@RequestParam("page") int page,
      @RequestParam("limit") int limit, @RequestParam("draftStatus") int draftStatus) {
    return new ResponseEntity<>(groupShoppingBlService.getGroupShoppings(page, limit, draftStatus),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取我创建的巨人车列表", notes = "根据分页和状态获取我创建的巨人车列表")
  @RequestMapping(value = "author", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getAuthorGroupShoppings(@RequestParam("status") int status) {
    return new ResponseEntity<>(
        groupShoppingBlService.getAuthorGroupShoppings(UserInfoUtil.getUsername(), status),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的巨人车列表", notes = "根据分页和状态获取我的巨人车列表")
  @RequestMapping(value = "me", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineGroupShoppings(@RequestParam("page") int page,
      @RequestParam("limit") int limit, @RequestParam("status") int status) {
    return new ResponseEntity<>(groupShoppingBlService
        .getUserGroupShoppings(page, limit, UserInfoUtil.getUsername(), status),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的巨人车列表", notes = "根据分页和状态获取我的巨人车列表")
  @RequestMapping(value = "participate/me", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineParticipateGroupShoppings() {
    return new ResponseEntity<>(
        groupShoppingBlService.getUserParticipateGroupShoppings(UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }


  @ApiOperation(value = "获取巨人车", notes = "获取巨人车详情")
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getGroupShopping(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(groupShoppingBlService.getGroupShopping(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "删除巨人车", notes = "获取巨人车详情")
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteGroupShopping(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(groupShoppingBlService.deleteGroupShopping(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "巨人车买票", notes = "巨人车买票")
  @RequestMapping(value = "participate/{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> participateGroupShopping(@PathVariable("id") int id) {
    return new ResponseEntity<>(
        groupShoppingBlService.participateGroupShopping(id, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "更新巨人车", notes = "更新巨人车")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateGroupShopping(
      @RequestBody GroupShoppingUpdateParameters parameters) {
    return new ResponseEntity<>(groupShoppingBlService.updateGroupShopping(parameters),
        HttpStatus.OK);
  }
}
