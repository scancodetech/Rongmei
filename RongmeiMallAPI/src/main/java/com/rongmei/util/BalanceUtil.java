package com.rongmei.util;

import com.rongmei.exception.SystemException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

@Component
public class BalanceUtil {

  public static void transfer(String fromUsername, String toUsername, int totalAmount,
      String tokenId) throws SystemException {
    String baseServiceUrl =
        "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.payment");
    String uri = baseServiceUrl + "/balance/transfer";
    MultiValueMap<String, String> dataMap = new LinkedMultiValueMap<>();
    dataMap.add("fromUsername", fromUsername);
    dataMap.add("toUsername", toUsername);
    dataMap.add("totalAmount", totalAmount + "");
    dataMap.add("tokenId", tokenId);
    dataMap.add("host", EnvironmentUtil.getSpringProfilesActive());
    String result = HttpUtil.post(uri, dataMap);
    if (!result.contains("success")) {
      throw new SystemException();
    }
  }

  public static void transferByBigDecimal(BigDecimal totalAmount, String username, String type) throws SystemException {
    String baseServiceUrl =
            "https://api.dimension.pub/" + EnvironmentUtil.packEnvironment("rongmei.account.api");
    String uri = baseServiceUrl + "/pay/transfer/inner/bigDecimal?" +
            "total_amount="+totalAmount+
            "&from_username="+username+
            "&type="+type;
    System.out.println("url----------->"+uri);
    String result = HttpUtil.getString(uri);
    if (!result.contains("10000")) {
      throw new SystemException();
    }
  }
  public static BigDecimal KeepTwoDecimalPlaces(BigDecimal bigDecimal){
    return bigDecimal.setScale(2,   BigDecimal.ROUND_HALF_UP);
  }




}
