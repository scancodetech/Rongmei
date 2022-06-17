package com.rongmei.springcontroller;

import com.rongmei.blservice.recommend.RecommendBlService;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "recommend")
public class RecommendController {

  private final RecommendBlService recommendBlService;

  @Autowired
  public RecommendController(RecommendBlService recommendBlService) {
    this.recommendBlService = recommendBlService;
  }

  @ApiOperation(value = "获取推荐列表", notes = "获取推荐列表，根据推荐算法")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineChild(@RequestParam("page") int page,
      @RequestParam("limit") int limit,
      @RequestParam(value = "startFromId", defaultValue = "0") long startFromId,
      @RequestParam(value = "startFromType", defaultValue = "0") int startFromType) {
    return new ResponseEntity<>(
        recommendBlService
            .getRecommendList(page, limit, startFromId, startFromType, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }
}
