package com.rongmei.springcontroller;

import com.rongmei.blservice.relation.RelationBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.relation.FavoritesUpdateParameters;
import com.rongmei.parameters.relation.LikeRelationUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.relation.FavoritesGetResponse;
import com.rongmei.response.relation.LikeGetResponse;
import com.rongmei.response.relation.LikeStatisticsResponse;
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
@RequestMapping(value = "relation")
public class RelationController {

  private final RelationBlService relationBlService;

  @Autowired
  public RelationController(RelationBlService relationBlService) {
    this.relationBlService = relationBlService;
  }

  @ApiOperation(value = "新建藏品夹", notes = "新建藏品夹")
  @RequestMapping(value = "favorites", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateFavorites(
      @RequestBody FavoritesUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          relationBlService.updateFavorites(parameters, UserInfoUtil.getUsername()), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "删除藏品夹", notes = "删除藏品夹")
  @RequestMapping(value = "favorites/{id}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteFavorites(@PathVariable("id") long id) {
    try {
      return new ResponseEntity<>(relationBlService.deleteFavorites(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取藏品夹", notes = "获取我的藏品夹")
  @RequestMapping(value = "favorites/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = FavoritesGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineFavorites() {
    return new ResponseEntity<>(relationBlService.getFavorites(UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "喜欢或取消喜欢", notes = "喜欢或取消喜欢")
  @RequestMapping(value = "like", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> postLike(@RequestBody LikeRelationUpdateParameters parameters) {
    return new ResponseEntity<>(relationBlService.postLike(parameters, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取喜欢信息", notes = "获取现在是否喜欢")
  @RequestMapping(value = "like", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = LikeGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getLike(@RequestParam("relation_id") int relationId,
      @RequestParam("type") int type) {
    return new ResponseEntity<>(
        relationBlService.getLike(relationId, type, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取喜欢信息", notes = "获取现在是否喜欢")
  @RequestMapping(value = "like/statistics/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = LikeStatisticsResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getLikeMineStatistics(@RequestParam("type") int type,
      @RequestParam("startTime") long startTime, @RequestParam("endTime") long endTime) {
    return new ResponseEntity<>(
        relationBlService
            .getLikeUserStatistics(type, UserInfoUtil.getUsername(), startTime, endTime),
        HttpStatus.OK);
  }
}
