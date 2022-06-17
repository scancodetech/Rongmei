package com.rongmei.springcontroller.lottery;

import com.rongmei.blservice.lottery.LotteryBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.exam.ExerciseUpdateParameters;
import com.rongmei.parameters.lottery.LotterySubmitParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
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
@RequestMapping(value = "lottery")
public class LotteryController {

  private final LotteryBlService lotteryBlService;

  @Autowired
  public LotteryController(LotteryBlService lotteryBlService) {
    this.lotteryBlService = lotteryBlService;
  }

  @ApiOperation(value = "新增中奖纪录", notes = "新增中奖纪录")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> submitLotteryRecord(
      @RequestBody LotterySubmitParameters parameters) {
    return new ResponseEntity<>(
        lotteryBlService.submitLotteryRecord(parameters, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取中奖纪录", notes = "获取中奖纪录")
  @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getLotteryRecord(@RequestParam("limit") int limit,
      @RequestParam("page") int page) {
    return new ResponseEntity<>(lotteryBlService.getLotteryRecord(limit, page), HttpStatus.OK);
  }

}
