package com.rongmei.springcontroller;

import com.rongmei.blservice.commodity.CommodityBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.commodity.CommodityQueryParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParamters2;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.commodity.CommoditySaleStatisticsResponse;
import com.rongmei.response.commodity.CommodityDetailResponse;
import com.rongmei.response.commodity.CommodityGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import java.util.List;

@RestController
@RequestMapping(value = "commodity")
public class CommodityController {

  private final CommodityBlService commodityBlService;

  @Autowired
  public CommodityController(CommodityBlService commodityBlService) {
    this.commodityBlService = commodityBlService;
  }

  @ApiOperation(value = "更新素材", notes = "更新素材")
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateCommodity(
      @RequestBody CommodityUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(commodityBlService.updateCommodity(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "更新多组素材", notes = "更新多组素材")
  @RequestMapping(value = "/group", method = RequestMethod.PUT)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = CommodityDetailResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateCommodity(
          @RequestBody List<CommodityUpdateParameters> parameters) {
      return new ResponseEntity<>(commodityBlService.updateCommodityByGroup(parameters), HttpStatus.OK);

  }

  @ApiOperation(value = "删除素材", notes = "删除素材")
  @RequestMapping(value = "", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteCommodity(@RequestParam("commodityId") int commodityId) {
    return new ResponseEntity<>(commodityBlService.deleteCommodity(commodityId), HttpStatus.OK);
  }

  @PreAuthorize("hasAnyAuthority('MATERIALSOURCE','ROLE_USER')")
  @ApiOperation(value = "获取素材", notes = "获取素材")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCommodities(@RequestBody CommodityQueryParameters parameters) {
    return new ResponseEntity<>(commodityBlService.getCommodities(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "获取推荐的素材", notes = "获取推荐的素材")
  @RequestMapping(value = "recommend", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getRecommendCommodities(@RequestParam("count") int count) {
    return new ResponseEntity<>(commodityBlService.getRecommendCommodities(count), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('MATERIALSOURCE')")
  @ApiOperation(value = "获取某个素材", notes = "根据ID获取某素材素材")
  @RequestMapping(value = "{commodityId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCommodity(@PathVariable("commodityId") int commodityId) {
    try {
      return new ResponseEntity<>(commodityBlService.getCommodity(commodityId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAnyAuthority('MATERIALSOURCE','ROLE_USER')")
  @ApiOperation(value = "获取某个素材", notes = "根据ID获取某素材素材")
  @RequestMapping(value = "author/{commodityId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCommodityAuthor(@PathVariable("commodityId") int commodityId) {
    try {
      return new ResponseEntity<>(commodityBlService.getCommodityAuthor(commodityId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取组素材", notes = "根据组ID获取素材")
  @RequestMapping(value = "group", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getUserGroupCommodities(
      @RequestParam("userGroupId") int userGroupId) {
    return new ResponseEntity<>(commodityBlService.getUserGroupCommodities(userGroupId),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取作者素材", notes = "获取作者的所有素材")
  @RequestMapping(value = "author", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getAuthorCommodities(
      @RequestParam(value = "status", defaultValue = "-1") int status) {
    return new ResponseEntity<>(
        commodityBlService.getAuthorCommodities(status, UserInfoUtil.getUsername()), HttpStatus.OK);
  }

  @ApiOperation(value = "获取话题素材", notes = "获取话题素材")
  @RequestMapping(value = "topic", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopicCommodities(@RequestParam("key") String key,
      @RequestParam("offset") int offset, @RequestParam("limit") int limit) {
    return new ResponseEntity<>(commodityBlService.getTopicCommodities(key, offset, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取收藏素材", notes = "获取收藏素材")
  @RequestMapping(value = "favorites", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getFavoritesCommodities(
      @RequestParam("favorites_id") int favoritesId, @RequestParam("status") int status) {
    return new ResponseEntity<>(
        commodityBlService.getFavoritesCommodities(favoritesId, status, UserInfoUtil
            .getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "移动收藏素材", notes = "移动收藏素材")
  @RequestMapping(value = "favorites/move", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> moveFavoritesCommodity(
      @RequestParam("favorites_id") int favoritesId,
      @RequestParam("commodity_id") int commodityId,
      @RequestParam("status") int status) {
    return new ResponseEntity<>(
        commodityBlService.moveFavoritesCommodity(favoritesId, commodityId, status),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取用户数据统计", notes = "获取用户数据统计，参与人数")
  @RequestMapping(value = "statistics/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommoditySaleStatisticsResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineStatistics(@RequestParam("startTime") long startTime,
      @RequestParam("endTime") long endTime) {
    return new ResponseEntity<>(
        commodityBlService.getUserStatistics(UserInfoUtil.getUsername(), startTime, endTime),
        HttpStatus.OK);
  }
}
