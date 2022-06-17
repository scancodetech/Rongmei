package com.rongmei.springcontroller;

import com.rongmei.blservice.blindbox.BlindBoxBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindbox.BlindBoxUpdateParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.blindbox.BlindBoxGetResponse;
import com.rongmei.response.commodity.CommodityDetailResponse;
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
@RequestMapping(value = "blind_box")
public class BlindBoxController {

  private final BlindBoxBlService blindBoxBlService;

  @Autowired
  public BlindBoxController(BlindBoxBlService blindBoxBlService) {
    this.blindBoxBlService = blindBoxBlService;
  }

  @ApiOperation(value = "更新盲盒", notes = "更新盲盒")
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateBlindBox(
      @RequestBody BlindBoxUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(blindBoxBlService.updateBlindBox(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取盲盒信息", notes = "获取目标id盲盒信息")
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateBlindBox(@PathVariable("id") long id) {
    try {
      return new ResponseEntity<>(blindBoxBlService.getBlindBoxDetail(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取盲盒列表", notes = "获取目标主题或系列盲盒列表")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBlindBoxList(
      @RequestParam(value = "theme", defaultValue = "") String theme,
      @RequestParam(value = "classification", defaultValue = "") String classification) {
    return new ResponseEntity<>(blindBoxBlService.getBlindBoxList(theme, classification),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取推荐盲盒列表", notes = "获取推荐盲盒列表")
  @RequestMapping(value = "recommend", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getRecommendBlindBoxList() {
    return new ResponseEntity<>(blindBoxBlService.getRecommendBlindBoxList(UserInfoUtil.getUsername()), HttpStatus.OK);
  }
}
