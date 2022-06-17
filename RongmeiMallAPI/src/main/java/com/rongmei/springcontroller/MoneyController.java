package com.rongmei.springcontroller;

import com.rongmei.blservice.money.MoneyBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.parameters.money.RechargeParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.commodity.CommodityGetResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "money")
public class MoneyController {

  private final MoneyBlService moneyBlService;

  @Autowired
  public MoneyController(MoneyBlService moneyBlService) {
    this.moneyBlService = moneyBlService;
  }

  @ApiOperation(value = "充值积分", notes = "充值积分")
  @RequestMapping(value = "recharge", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> recharge(@RequestBody RechargeParameters parameters) {
    try {
      return new ResponseEntity<>(moneyBlService.recharge(parameters), HttpStatus.OK);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
