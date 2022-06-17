package com.rongmei.bl.pay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.rongmei.blservice.pay.PayBlService;
import com.rongmei.dao.account.UserAccountDao;
import com.rongmei.dao.account.UserDao;
import com.rongmei.dao.account.UserInfoDao;
import com.rongmei.dao.account.UserSecurityDao;
import com.rongmei.dao.pay.DiscountLogDao;
import com.rongmei.dao.pay.PayOrderDao;
import com.rongmei.dao.pay.WithdrawRequestDao;
import com.rongmei.entity.account.User;
import com.rongmei.entity.account.UserAccount;
import com.rongmei.entity.account.UserInfo;
import com.rongmei.entity.account.UserSecurity;
import com.rongmei.entity.pay.DiscountLog;
import com.rongmei.entity.pay.PayOrder;
import com.rongmei.entity.pay.WithdrawRequest;
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
import com.rongmei.response.pay.RechargeItem;
import com.rongmei.response.pay.WechatPostResponse;
import com.rongmei.response.pay.WechatSignatureResponse;
import com.rongmei.response.pay.WithdrawGetResponse;
import com.rongmei.response.pay.WithdrawItem;
import com.rongmei.util.FormatDateTime;
import com.rongmei.util.SHA1;
import com.rongmei.util.PayTypeUtil;
import com.rongmei.util.UserInfoUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.StringUtils;
@Service
public class PayBlServiceImpl implements PayBlService {

  @Value(value = "${alipay.appId}")
  private String appId;
  @Value(value = "${alipay.merchantPrivateKey}")
  private String merchantPrivateKey;
  @Value(value = "${alipay.alipayPublicKey}")
  private String alipayPublicKey;
  @Value(value = "${alipay.subject}")
  private String subject;
  @Value(value = "${alipay.notifyUrl}")
  private String notifyUrl;
  @Value(value = "${alipay.signType}")
  private String signType;

  @Value(value = "${wechat.pay.v3.mobile.app-id}")
  private String wechatAppId;
  @Value(value = "${wechat.pay.v3.mobile.app-secret}")
  private String wechatAppSecret;

  private final WxPayService wxPayService;
  private final PayOrderDao payOrderDao;
  private final WithdrawRequestDao withdrawRequestDao;
  private final UserInfoDao userInfoDao;
  private final UserDao userDao;
  private final UserAccountDao userAccountDao;
  private final UserSecurityDao userSecurityDao;
  private final DiscountLogDao discountLogDao;

  @Autowired
  public PayBlServiceImpl(WxPayService wxPayService,
      PayOrderDao payOrderDao,
      WithdrawRequestDao withdrawRequestDao, UserInfoDao userInfoDao,
      UserDao userDao, UserAccountDao userAccountDao,
      UserSecurityDao userSecurityDao, DiscountLogDao discountLogDao) {
    this.wxPayService = wxPayService;
    this.payOrderDao = payOrderDao;
    this.withdrawRequestDao = withdrawRequestDao;
    this.userInfoDao = userInfoDao;
    this.userDao = userDao;
    this.userAccountDao = userAccountDao;
    this.userSecurityDao = userSecurityDao;
    this.discountLogDao = discountLogDao;
  }

  @PostConstruct
  private void init() {
    // 1. 设置参数（全局只需设置一次）
    Factory.setOptions(getOptions());
  }

  private Config getOptions() {
    Config config = new Config();
    config.protocol = "https";
    config.gatewayHost = "openapi.alipay.com";
    config.signType = signType;

    config.appId = appId;
    config.merchantPrivateKey = merchantPrivateKey;
    config.alipayPublicKey = alipayPublicKey;
    config.notifyUrl = notifyUrl;

    return config;
  }

  @Override
  public AlipayPostResponse postAlipay(AlipayPostParameters postParameters) throws SystemException {
    String username = UserInfoUtil.getUsername();
    String orderNo = FormatDateTime.currentRandomTimeString();
    payOrderDao.save(new PayOrder(orderNo, "未支付", username, postParameters.getTotalAmount(),
        System.currentTimeMillis(), System.currentTimeMillis(), postParameters.getPayType(),
            postParameters.getAlipayAccount(),"0"));

    return alipayPost(postParameters, orderNo);
  }

  @Override
  public SuccessResponse withdraw(WithdrawParameters withdrawParameters) {
    if (withdrawParameters.getUsername().length() == 0) {
      withdrawParameters.setUsername(UserInfoUtil.getUsername());
    }
    String alipayAccount = withdrawParameters.getAlipayAccount();
    String wechatAccount = withdrawParameters.getWechatAccount();

    if(StringUtils.isEmpty(alipayAccount)){
      alipayAccount = "0";
    }
    if(StringUtils.isEmpty(wechatAccount)){
      wechatAccount = "0";
    }
    WithdrawRequest withdrawRequest = new WithdrawRequest("未完成", withdrawParameters.getUsername(),
        withdrawParameters.getCoins(), alipayAccount,wechatAccount
        , System.currentTimeMillis(),
        System.currentTimeMillis());
    withdrawRequestDao.save(withdrawRequest);
    return new SuccessResponse("submit withdraw request success");
  }

  @Override
  public WithdrawGetResponse getWithdrawList() {
    List<WithdrawRequest> withdrawRequestList = withdrawRequestDao.findAll();
    List<WithdrawItem> withdrawItems = new ArrayList<>();
    for (WithdrawRequest withdrawRequest : withdrawRequestList) {
      User user = userDao.findFirstByPhone(withdrawRequest.getUsername());
      if (user != null) {
        UserInfo userInfo = userInfoDao.findUserInfoByUserId(user.getId());
        WithdrawItem withdrawItem = new WithdrawItem(withdrawRequest.getId(),
            withdrawRequest.getStatus(), user, userInfo, withdrawRequest.getTotalAmount(),
            withdrawRequest.getCreateTime(), withdrawRequest.getUpdateTime(),
                withdrawRequest.getAlipayAccount(),withdrawRequest.getWechatAccount());


        withdrawItems.add(withdrawItem);
      }
    }
    return new WithdrawGetResponse(withdrawItems);
  }

  @Override
  public RechargeGetResponse getRechargeList() {
    List<PayOrder> payOrderList = payOrderDao.findAll();
    List<RechargeItem> rechargeItems = new ArrayList<>();
    for (PayOrder payOrder : payOrderList) {
      User user = userDao.findFirstByPhone(payOrder.getUsername());
      if (user != null) {
        UserInfo userInfo = userInfoDao.findUserInfoByUserId(user.getId());
        RechargeItem rechargeItem = new RechargeItem(payOrder.getId(),
            payOrder.getStatus(), user, userInfo, payOrder.getTotalAmount(),
            payOrder.getCreateTime(), payOrder.getUpdateTime(), payOrder.getPayType(),
                payOrder.getAlipayAccount(),payOrder.getWechatAccount());
        rechargeItems.add(rechargeItem);
      }
    }
    return new RechargeGetResponse(rechargeItems);
  }

  @Override
  public SuccessResponse doneWithdraw(int requestId) {
    Optional<WithdrawRequest> optionalWithdrawRequest = withdrawRequestDao.findById(requestId);
    if (optionalWithdrawRequest.isPresent()) {
      WithdrawRequest withdrawRequest = optionalWithdrawRequest.get();
      withdrawRequest.setStatus("已完成");
      withdrawRequestDao.save(withdrawRequest);
    }
    return new SuccessResponse("done withdraw");
  }

  @Override
  public String alipayNotify(double totalAmount, String outTradeNo) {
    System.out.println(outTradeNo);
    System.out.println(totalAmount);
    Optional<PayOrder> optionalPayOrder = payOrderDao.findById(outTradeNo);
    if (optionalPayOrder.isPresent()) {
      PayOrder payOrder = optionalPayOrder.get();
      if (payOrder.getStatus().equals("已支付")) {
        return "success";
      }
      payOrder.setStatus("已支付");
      payOrderDao.save(payOrder);
      User user = userDao.findFirstByPhone(payOrder.getUsername());
      if (user != null) {
        UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
        if (userAccount == null) {
          userAccount = new UserAccount(user.getId(), Math.round(totalAmount * 100), 0, 0, 0);
        } else {
          userAccount.setLargeCoins(userAccount.getLargeCoins() + Math.round(totalAmount * 100));
        }
        userAccountDao.save(userAccount);
      }
    }
    return "success";
  }

  @Override
  public SuccessResponse createPayOrder(PayOrderCreationParameters payOrderCreationParameters) {
    String orderNo = FormatDateTime.currentRandomTimeString();
    String alipayAccount = payOrderCreationParameters.getAlipayAccount();
    String wechatAccount = payOrderCreationParameters.getWechatAccount();
    if(StringUtils.isEmpty(alipayAccount)){
      alipayAccount = "0";
    }
    if(StringUtils.isEmpty(wechatAccount)){
      wechatAccount = "0";
    }
    payOrderDao
        .save(new PayOrder(orderNo, "中奖", payOrderCreationParameters.getUsername(),
            payOrderCreationParameters.getTotalAmount(),
            System.currentTimeMillis(), System.currentTimeMillis(), 0,
                alipayAccount,wechatAccount));
    User user = userDao.findFirstByPhone(payOrderCreationParameters.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      userAccount.setLargeCoins(
          userAccount.getLargeCoins() + (long) payOrderCreationParameters.getTotalAmount() * 100);
      userAccountDao.save(userAccount);
    }
    return new SuccessResponse("create success");
  }

  @Override
  public synchronized SuccessResponse discount(int totalAmount, String username)
      throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(username);
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getLargeCoins() + userAccount.getDisableWithDrawCoins() >= totalAmount) {
        long resCoins = 0;
        if (userAccount.getDisableWithDrawCoins() >= totalAmount) {
          userAccount.setDisableWithDrawCoins(
              userAccount.getDisableWithDrawCoins() - (long) totalAmount);
          DiscountLog discountLog = new DiscountLog(username, totalAmount, 1,
              System.currentTimeMillis(),
              System.currentTimeMillis());
          discountLogDao.save(discountLog);
        } else {
          userAccount.setDisableWithDrawCoins(0L);
          resCoins = totalAmount - userAccount.getDisableWithDrawCoins();
          DiscountLog discountLog = new DiscountLog(username, userAccount.getDisableWithDrawCoins(),
              1,
              System.currentTimeMillis(),
              System.currentTimeMillis());
          discountLogDao.save(discountLog);
        }
        userAccount.setLargeCoins(userAccount.getLargeCoins() - resCoins);
        if (resCoins > 0) {
          DiscountLog discountLog = new DiscountLog(username, resCoins, 0,
              System.currentTimeMillis(), System.currentTimeMillis());
          discountLogDao.save(discountLog);
        }
        userAccountDao.save(userAccount);
        return new SuccessResponse("discount success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public synchronized SuccessResponse transfer(int totalAmount, String fromUsername,
      String toUsername) throws ThingIdDoesNotExistException {
    User fromUser = userDao.findFirstByPhone(fromUsername);
    User toUser = userDao.findFirstByPhone(toUsername);
    if (fromUser != null && toUser != null) {
      UserAccount fromUserAccount = userAccountDao.findFirstByUserId(fromUser.getId());
      UserAccount toUserAccount = userAccountDao.findFirstByUserId(toUser.getId());
      if (fromUserAccount.getLargeCoins() + fromUserAccount.getDisableWithDrawCoins()
          >= totalAmount) {
        long resCoins = 0;
        if (fromUserAccount.getDisableWithDrawCoins() >= totalAmount) {
          fromUserAccount.setDisableWithDrawCoins(
              fromUserAccount.getDisableWithDrawCoins() - (long) totalAmount);
          toUserAccount
              .setDisableWithDrawCoins(toUserAccount.getDisableWithDrawCoins() + totalAmount);
        } else {
          toUserAccount.setDisableWithDrawCoins(
              toUserAccount.getDisableWithDrawCoins() + fromUserAccount.getDisableWithDrawCoins());
          resCoins = totalAmount - fromUserAccount.getDisableWithDrawCoins();
          fromUserAccount.setDisableWithDrawCoins(0L);
        }
        fromUserAccount.setLargeCoins(fromUserAccount.getLargeCoins() - resCoins);
        toUserAccount.setLargeCoins(toUserAccount.getLargeCoins() + resCoins);
        userAccountDao.save(fromUserAccount);
        userAccountDao.save(toUserAccount);
        return new SuccessResponse("transfer success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public synchronized SuccessResponse transferEarnest(int totalAmount)
      throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getLargeCoins() + userAccount.getDisableWithDrawCoins() >= totalAmount) {
        discount(totalAmount, UserInfoUtil.getUsername());
        userAccount.setEarnestCoins(userAccount.getEarnestCoins() + totalAmount);
        userAccountDao.save(userAccount);
        return new SuccessResponse("transfer success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public synchronized SuccessResponse discountEarnest(int totalAmount, String username)
      throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getEarnestCoins() >= totalAmount) {
        userAccount.setEarnestCoins(userAccount.getEarnestCoins() - totalAmount);
        userAccount.setDisableWithDrawCoins(userAccount.getDisableWithDrawCoins() + totalAmount);
        userAccountDao.save(userAccount);
        return new SuccessResponse("discount success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse discountDisableWithdraw(int totalAmount)
      throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getDisableWithDrawCoins() >= totalAmount) {
        userAccount.setDisableWithDrawCoins(userAccount.getDisableWithDrawCoins() - totalAmount);
        DiscountLog discountLog = new DiscountLog(user.getPhone(), totalAmount, 1,
            System.currentTimeMillis(),
            System.currentTimeMillis());
        discountLogDao.save(discountLog);
        userAccountDao.save(userAccount);
        return new SuccessResponse("transfer success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse discountCoins(int totalAmount) throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getLargeCoins() >= totalAmount) {
        userAccount.setLargeCoins(userAccount.getLargeCoins() - totalAmount);
        DiscountLog discountLog = new DiscountLog(user.getPhone(), totalAmount, 0,
            System.currentTimeMillis(),
            System.currentTimeMillis());
        discountLogDao.save(discountLog);
        userAccountDao.save(userAccount);
        return new SuccessResponse("transfer success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse validatePayCode(String code)
      throws ThingIdDoesNotExistException, WrongUsernameOrPasswordException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserSecurity userSecurity = userSecurityDao.findFirstByUserId(user.getId());
      if (userSecurity != null) {
        if (userSecurity.getPayNum().equals(code)) {
          return new SuccessResponse("validate success");
        } else {
          throw new WrongUsernameOrPasswordException();
        }
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public WechatPostResponse postWechat(WechatPostParameters postParameters) throws SystemException {
    return new WechatPostResponse(createOrderInfo(postParameters));
  }

  @Override
  public SuccessResponse addLotteryNum(String username, int count)
      throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (count > 0) {
        userAccount.setLotteryNum(userAccount.getLotteryNum() + count);
        userAccountDao.save(userAccount);
        return new SuccessResponse("add lottery num success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse discountLotteryNum(String username) throws ThingIdDoesNotExistException {
    User user = userDao.findFirstByPhone(UserInfoUtil.getUsername());
    if (user != null) {
      UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
      if (userAccount.getLotteryNum() > 0) {
        userAccount.setLotteryNum(userAccount.getLotteryNum() - 1);
        userAccountDao.save(userAccount);
        return new SuccessResponse("discount lottery num success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public String wechatNotify(String xmlData) {
    System.out.println(xmlData);
    try {
      final WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
      String orderNo = notifyResult.getOutTradeNo();
      System.out.println("=== orderNo ===");
      System.out.println(orderNo);
      Optional<PayOrder> optionalPayOrder = payOrderDao.findById(orderNo);
      if (optionalPayOrder.isPresent()) {
        PayOrder payOrder = optionalPayOrder.get();
        System.out.println("=== orderUsername ===");
        System.out.println(payOrder.getUsername());
        if (payOrder.getStatus().equals("已支付")) {
          return "success";
        }
        payOrder.setStatus("已支付");
        payOrderDao.save(payOrder);
        User user = userDao.findFirstByPhone(payOrder.getUsername());
        if (user != null) {
          UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
          if (userAccount == null) {
            userAccount = new UserAccount(user.getId(), Math.round(payOrder.getTotalAmount() * 100),
                0, 0, 0);
          } else {
            userAccount.setLargeCoins(
                userAccount.getLargeCoins() + Math.round(payOrder.getTotalAmount() * 100));
          }
          userAccountDao.save(userAccount);
        }
      }
      return WxPayNotifyResponse.success("回调成功！");
    } catch (WxPayException e) {
      e.printStackTrace();
      return WxPayNotifyResponse.fail("回调有误!");
    }
  }

  @Override
  public WechatSignatureResponse packWechatSignature(WechatSignatureParameters parameters) {
    RestTemplate restTemplate = new RestTemplate();
    JSONObject jsonRes = restTemplate.getForObject(
            String.format(
                    "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                    wechatAppId, wechatAppSecret),
            JSONObject.class);
    String accessToken = (String) jsonRes.get("access_token");
    jsonRes = restTemplate.getForObject(
            String.format(
                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
                    accessToken),
            JSONObject.class);
    String ticket = (String) jsonRes.get("ticket");
    String signature = SHA1.SHA1Encode(
            "jsapi_ticket=" + ticket + "&noncestr=" + parameters.getNonceStr() + "&timestamp="
                    + parameters.getTimestamp() + "&url=" + parameters.getUrl());
    return new WechatSignatureResponse(signature);
  }

  @Override
  public SuccessResponse transferByBigDecimal(BigDecimal totalAmount,
                                              String username,
                                              String type) throws ThingIdDoesNotExistException{
    User user = userDao.findFirstByPhone(username);
    UserAccount userAccount = userAccountDao.findFirstByUserId(user.getId());
    if (user != null) {
      if("支出".equals(type)){
        if (userAccount.getLargeCoins() + userAccount.getDisableWithDrawCoins()
                >= totalAmount.longValue()) {
          if (userAccount.getDisableWithDrawCoins() >= totalAmount.longValue()) {
            userAccount.setDisableWithDrawCoins(
                    userAccount.getDisableWithDrawCoins() - totalAmount.longValue());
          } else {
            long  resCoins = totalAmount.longValue() - userAccount.getDisableWithDrawCoins();
            userAccount.setDisableWithDrawCoins(0L);
            userAccount.setLargeCoins(userAccount.getLargeCoins()-resCoins);
          }
          userAccountDao.save(userAccount);
          return new SuccessResponse("transfer success");
        } else {
          throw new ThingIdDoesNotExistException();
        }
      }

      userAccount.setLargeCoins(userAccount.getLargeCoins()+totalAmount.longValue());
      userAccountDao.save(userAccount);
      return new SuccessResponse("transfer success");

    }
      throw new ThingIdDoesNotExistException();
  }

  private String createOrderInfo(WechatPostParameters postParameters) throws SystemException {
    WxPayMwebOrderResult result;
    try {
      WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
      String orderNo = FormatDateTime.currentRandomTimeString();
      orderRequest.setOutTradeNo(orderNo);
      orderRequest.setBody("跨次元");
      orderRequest.setTotalFee((int) Math.ceil(postParameters.getTotalAmount() * 100));
      orderRequest.setSpbillCreateIp(postParameters.getIp());
      orderRequest.setProductId(orderNo);
      orderRequest.setTradeType(WxPayConstants.TradeType.MWEB);// h5网页支付
      result = wxPayService.createOrder(orderRequest);
      payOrderDao.save(
          new PayOrder(orderNo, "未支付", UserInfoUtil.getUsername(), postParameters.getTotalAmount(),
              System.currentTimeMillis(), System.currentTimeMillis(), postParameters.getPayType(),"0",postParameters.getWechatAccount()));
      return result.getMwebUrl();
    } catch (WxPayException e) {
      System.out.println(e.getReturnMsg());
      System.out.println(e.getMessage());
      System.out.println(e.getCustomErrorMsg());
      System.out.println(e.getErrCodeDes());
      System.out.println(e.getXmlString());
      e.printStackTrace();
      throw new SystemException();
    }
  }

  private AlipayPostResponse alipayPost(AlipayPostParameters postParameters, String orderNo)
      throws SystemException {
    try {
      if (postParameters.getPayType() == 0) {
        AlipayTradeWapPayResponse response = Payment.Wap()
            .pay(subject, orderNo, String.valueOf(postParameters.getTotalAmount()), "",
                postParameters.getReturnUrl());
        if (ResponseChecker.success(response)) {
          return new AlipayPostResponse(response.getBody());
        } else {
          System.err.println("调用失败，原因：" + response.getBody());
          throw new SystemException();
        }
      } else if (postParameters.getPayType() == 1) {
        AlipayTradePagePayResponse response = Payment.Page()
            .pay(subject, orderNo, String.valueOf(postParameters.getTotalAmount()),
                postParameters.getReturnUrl());
        if (ResponseChecker.success(response)) {
          return new AlipayPostResponse(response.getBody());
        } else {
          System.err.println("调用失败，原因：" + response.getBody());
          throw new SystemException();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new SystemException();
    }
    return new AlipayPostResponse("error pay type");
  }
}
