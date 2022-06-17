package com.rongmei.springcontroller.pay;


import com.github.binarywang.wxpay.service.WxPayService;
import com.rongmei.blservice.pay.PayBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.pay.AlipayPostParameters;
import com.rongmei.parameters.pay.PayOrderCreationParameters;
import com.rongmei.parameters.pay.WechatPostParameters;
import com.rongmei.parameters.pay.WechatSignatureParameters;
import com.rongmei.parameters.pay.WithdrawParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "pay")
public class PayController {

  private final PayBlService payBlService;

  @Autowired
  public PayController(PayBlService payBlService) {
    this.payBlService = payBlService;
  }

  @ApiOperation(value = "调起支付宝支付", notes = "调起支付宝支付")
  @RequestMapping(method = RequestMethod.POST, path = "alipay", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> postAlipay(@RequestBody AlipayPostParameters postParameters) {
    try {
      return new ResponseEntity<>(payBlService.postAlipay(postParameters), HttpStatus.OK);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ApiOperation(value = "获取微信签名", notes = "获取微信签名")
  @RequestMapping(method = RequestMethod.POST, path = "wechat/signature", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> packWechatSignature(@RequestBody WechatSignatureParameters parameters) {
    return new ResponseEntity<>(payBlService.packWechatSignature(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "调起微信支付", notes = "调起微信支付")
  @RequestMapping(method = RequestMethod.POST, path = "wechat", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> postWechat(@RequestBody WechatPostParameters postParameters) {
    try {
      return new ResponseEntity<>(payBlService.postWechat(postParameters), HttpStatus.OK);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ApiOperation(value = "创建订单", notes = "创建订单")
  @RequestMapping(method = RequestMethod.POST, path = "order/creation", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> createPayOrder(
          @RequestBody PayOrderCreationParameters payOrderCreationParameters) {
    return new ResponseEntity<>(payBlService.createPayOrder(payOrderCreationParameters),
            HttpStatus.OK);
  }

  @ApiOperation(value = "支付宝支付回调", notes = "支付宝支付回调")
  @RequestMapping(method = RequestMethod.POST, path = "alipay/notify", produces = "application/json")
  @ResponseBody
  public String alipayNotify(@RequestParam("total_amount") double totalAmount,
                             @RequestParam("out_trade_no") String outTradeNo) {
    return payBlService.alipayNotify(totalAmount, outTradeNo);
  }

  @ApiOperation(value = "微信支付回调", notes = "微信支付回调")
  @RequestMapping(value = "wechat/notify")
  @ResponseBody
  public String wechatNotify(HttpServletRequest request) {
    System.out.println("微信支付回调");
    StringBuilder xmlString = new StringBuilder();
    InputStream is = null;
    try {
      is = request.getInputStream();
      // 将InputStream转换成String
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = reader.readLine()) != null) {
        xmlString.append(line).append("\n");
      }
    } catch (Exception e) {
      System.out.println("微信手机支付回调通知失败：" + e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return this.payBlService.wechatNotify(xmlString.toString());
  }

  @ApiOperation(value = "消费积分", notes = "消费积分,先消费不可积分再消费可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount/username/inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discount(@RequestParam("total_amount") int totalAmount,
                                           @RequestParam("username") String username) {
    try {
      return new ResponseEntity<>(payBlService.discount(totalAmount, username), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "消费积分", notes = "消费积分,先消费不可提现积分再消费可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discount(@RequestParam("total_amount") int totalAmount) {
    try {
      return new ResponseEntity<>(payBlService.discount(totalAmount, UserInfoUtil.getUsername()),
              HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "消费可提现积分", notes = "消费可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount/coins", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discountCoins(@RequestParam("total_amount") int totalAmount) {
    try {
      return new ResponseEntity<>(payBlService.discountCoins(totalAmount), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "消费不可提现积分", notes = "消费不可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount/disableWithdraw", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discountDisableWithdraw(
          @RequestParam("total_amount") int totalAmount) {
    try {
      return new ResponseEntity<>(payBlService.discountDisableWithdraw(totalAmount), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "转移积分", notes = "转移积分（内部接口）")
  @RequestMapping(method = RequestMethod.GET, path = "transfer/inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> transfer(@RequestParam("total_amount") int totalAmount,
                                           @RequestParam("from_username") String fromUsername,
                                           @RequestParam("to_username") String toUsername) {
    try {
      return new ResponseEntity<>(payBlService.transfer(totalAmount, fromUsername, toUsername),
              HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }


  @ApiOperation(value = "转移积分", notes = "转移积分（内部接口）")
  @RequestMapping(method = RequestMethod.GET, path = "transfer/inner/bigDecimal", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> transferByBigDecimal(@RequestParam("total_amount") BigDecimal totalAmount,
                                                       @RequestParam("from_username") String fromUsername,
                                                       @RequestParam("type") String type) {
    try {
      return new ResponseEntity<>(payBlService.transferByBigDecimal(totalAmount, fromUsername, type),
              HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "转移积分", notes = "转移积分")
  @RequestMapping(method = RequestMethod.GET, path = "transfer", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> transfer(@RequestParam("total_amount") int totalAmount,
                                           @RequestParam("to_username") String toUsername) {
    try {
      return new ResponseEntity<>(
              payBlService.transfer(totalAmount, UserInfoUtil.getUsername(), toUsername),
              HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "", notes = "转移积分到保证金，如果不够则提示充值，将积分转到保证金")
  @RequestMapping(method = RequestMethod.GET, path = "transfer/earnest", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> transferEarnest(@RequestParam("total_amount") int totalAmount) {
    try {
      return new ResponseEntity<>(payBlService.transferEarnest(totalAmount), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException | SystemException e) {
      e.printStackTrace();
      if (e instanceof ThingIdDoesNotExistException) {
        return new ResponseEntity<>(((ThingIdDoesNotExistException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
      if (e instanceof SystemException) {
        return new ResponseEntity<>(((SystemException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
    }
    return null;
  }


  @ApiOperation(value = "取出保证金", notes = "取出保证金，将保证金转到不可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount/earnest", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discountEarnest(@RequestParam("total_amount") int totalAmount) {
    try {
      return new ResponseEntity<>(
          payBlService.discountEarnest(totalAmount, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException | SystemException e) {
      e.printStackTrace();
      if (e instanceof ThingIdDoesNotExistException) {
        return new ResponseEntity<>(((ThingIdDoesNotExistException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
      if (e instanceof SystemException) {
        return new ResponseEntity<>(((SystemException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
    }
    return null;
  }

  @ApiOperation(value = "取出保证金", notes = "取出保证金，将保证金转到不可提现积分")
  @RequestMapping(method = RequestMethod.GET, path = "discount/earnest/inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discountEarnestInner(
      @RequestParam("total_amount") int totalAmount, @RequestParam("username") String username) {
    try {
      return new ResponseEntity<>(
          payBlService.discountEarnest(totalAmount, username),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException | SystemException e) {
      e.printStackTrace();
      if (e instanceof ThingIdDoesNotExistException) {
        return new ResponseEntity<>(((ThingIdDoesNotExistException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
      if (e instanceof SystemException) {
        return new ResponseEntity<>(((SystemException) e).getResponse(), HttpStatus.NOT_FOUND);
      }
    }
    return null;
  }

  @ApiOperation(value = "发起提现申请", notes = "发起提现申请")
  @RequestMapping(method = RequestMethod.POST, path = "withdraw", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> withdraw(@RequestBody WithdrawParameters withdrawParameters) {
    return new ResponseEntity<>(payBlService.withdraw(withdrawParameters), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('WITHDRAWAL')")
  @ApiOperation(value = "完成提现申请", notes = "完成提现申请")
  @RequestMapping(method = RequestMethod.GET, path = "withdraw/done/${requestId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> doneWithdraw(@PathVariable("requestId") int requestId) {
    return new ResponseEntity<>(payBlService.doneWithdraw(requestId), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('WITHDRAWAL')")
  @ApiOperation(value = "获取提现列表", notes = "获取提现列表")
  @RequestMapping(method = RequestMethod.GET, path = "withdraw", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getWithdrawList() {
    return new ResponseEntity<>(payBlService.getWithdrawList(), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('RECHARGE')")
  @ApiOperation(value = "获取充值列表", notes = "获取充值列表")
  @RequestMapping(method = RequestMethod.GET, path = "recharge", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getRechargeList() {
    return new ResponseEntity<>(payBlService.getRechargeList(), HttpStatus.OK);
  }

  @ApiOperation(value = "验证支付密码", notes = "验证支付密码")
  @RequestMapping(method = RequestMethod.GET, path = "code", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> validatePayCode(@RequestParam("code") String code) {
    try {
      return new ResponseEntity<>(payBlService.validatePayCode(code), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (WrongUsernameOrPasswordException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.FORBIDDEN);
    }
  }

  @ApiOperation(value = "增加抽奖次数", notes = "增加抽奖次数")
  @RequestMapping(method = RequestMethod.GET, path = "lottery", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> addLotteryNum(@RequestParam(name = "count") int count) {
    try {
      return new ResponseEntity<>(payBlService.addLotteryNum(UserInfoUtil.getUsername(), count),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "增加抽奖次数", notes = "增加抽奖次数")
  @RequestMapping(method = RequestMethod.GET, path = "lottery/inner", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> addLotteryNumInner(
      @RequestParam(name = "username") String username,
      @RequestParam(name = "count") int count) {
    try {
      return new ResponseEntity<>(payBlService.addLotteryNum(username, count), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "消除抽奖次数", notes = "消除抽奖次数")
  @RequestMapping(method = RequestMethod.GET, path = "lottery/discount", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> discountLotteryNum() {
    try {
      return new ResponseEntity<>(payBlService.discountLotteryNum(UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
