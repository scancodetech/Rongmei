package com.rongmei.blservice.pay;

import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.WrongUsernameOrPasswordException;
import com.rongmei.parameters.pay.AlipayPostParameters;
import com.rongmei.parameters.pay.PayOrderCreationParameters;
import com.rongmei.parameters.pay.WechatPostParameters;
import com.rongmei.parameters.pay.WechatSignatureParameters;
import com.rongmei.parameters.pay.WithdrawParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.pay.AlipayPostResponse;
import com.rongmei.response.pay.RechargeGetResponse;
import com.rongmei.response.pay.WechatPostResponse;
import com.rongmei.response.pay.WechatSignatureResponse;
import com.rongmei.response.pay.WithdrawGetResponse;

import java.math.BigDecimal;
import java.util.Map;

public interface PayBlService {

  AlipayPostResponse postAlipay(AlipayPostParameters postParameters) throws SystemException;

  SuccessResponse withdraw(WithdrawParameters withdrawParameters);

  WithdrawGetResponse getWithdrawList();

  RechargeGetResponse getRechargeList();

  SuccessResponse doneWithdraw(int requestId);

  String alipayNotify(double totalAmount, String outTradeNo);

  SuccessResponse createPayOrder(PayOrderCreationParameters payOrderCreationParameters);

  SuccessResponse discount(int totalAmount, String username) throws ThingIdDoesNotExistException;

  SuccessResponse transfer(int totalAmount, String fromUsername, String toUsername)
      throws ThingIdDoesNotExistException;

  SuccessResponse transferEarnest(int totalAmount) throws ThingIdDoesNotExistException, SystemException;

  SuccessResponse discountEarnest(int totalAmount, String username)
          throws ThingIdDoesNotExistException, SystemException;

  SuccessResponse discountDisableWithdraw(int totalAmount) throws ThingIdDoesNotExistException;

  SuccessResponse discountCoins(int totalAmount) throws ThingIdDoesNotExistException;

  SuccessResponse validatePayCode(String code)
      throws ThingIdDoesNotExistException, WrongUsernameOrPasswordException;

  WechatPostResponse postWechat(WechatPostParameters postParameters) throws SystemException;

  SuccessResponse addLotteryNum(String username, int count) throws ThingIdDoesNotExistException;

  SuccessResponse discountLotteryNum(String username) throws ThingIdDoesNotExistException;

  String wechatNotify(String xmlData);

  WechatSignatureResponse packWechatSignature(WechatSignatureParameters parameters);

  SuccessResponse transferByBigDecimal(BigDecimal totalAmount, String username,
                                       String type) throws ThingIdDoesNotExistException;
}
