package com.rongmei.bl.money;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.rongmei.blservice.money.MoneyBlService;
import com.rongmei.exception.SystemException;
import com.rongmei.parameters.money.RechargeParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.util.FormatDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MoneyBlServiceImpl implements MoneyBlService {

  @Value("${alipay.app_id}")
  private String appId;
  @Value("${alipay.gateway}")
  private String gateway;
  @Value("${alipay.sign_type}")
  private String signType;
  @Value("${alipay.charset}")
  private String charset;

  @Override
  public SuccessResponse recharge(RechargeParameters parameters) throws SystemException {
    try {
      //构造client
      AlipayClient alipayClient = new DefaultAlipayClient(gateway, appId, "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDMf6qG6pdBQRPFWA7qDANYNfUc/anOIKN6ZNCtFEvGn6JtOOFbTwWeLGp1PUX93ExP1terriWz57zEQmvuJdzkIrpVrZgWJVyJjlkrlTuck09RAQz4hGS5FclcjlSNDpeVUMwOrg74hRG2xuXYiWbha0x9HNg/JLJthAoI9lZYMOCH5MjhEITx9TvojP3Q5OOTAuK8elGXZZw8BoWGnuSWbpqZlyBPT3VQWE5OifcdhY0eTBLlPl687+WG1OPLLpxIPAenayqPuS31WC6KELRtdnYqEUrlOjdMT2bKgj24Ba+jlSf6jE7fym1cho3GdA5Rp3o6d+8RxyHOGLktJkf5AgMBAAECggEAG2ZbpdoWzBJ5IK7syKU1aYNJRUojj0/GN6b8YsReZs2KyNGfOEm9OfU/ilfcYeg7fVMO8JzTxmgdXbp8lZsTHqoch1BMGWOGI5QkFHfZVeBS90qJv16V1Eq8AR+86m1zF/mMbccakGfb+L5rm3jFhRyIUhckTkmPcqcHZS4EYaplZQ12xLwOf0U4UqRAIv80GN7AlDuTgPQkf6VtKKx2gWdTGvADlCXoyovNbXZLOfj69bS8SVbMu58C++EuZbUH349gWu1+Lgt/l85mnCClZq5yXiA4VGogVFfJMifXsSX6sWs2LwobGGYfuODeHClOnVjh1uef3bUTmOb3Q+svAQKBgQD1PHuOqB5uDKGRqtfh//X2yW3/1mjAE3ZMx8V2vak+0Bo9l90YbGKkDU12XTKXminRwSdB2U5vcxqab5yS5UD/sCYPqZ11wztwNuXoyG0x5xPnkXVoiViyVmNE76BI6S8cAXzYHleM7treHB8LAqsqT7Cb0MyHPylHslLf+i82IQKBgQDVeXL4VDWpTAPTD3tcgFeVpPf/D8/63KkJUOv+E7EhennmDSbJ7gS5sToD5ieC9NknW96zqElvWVm4vdRW4MFlxIJOkcr95qbM5SI7p10WgBeZ3KqBlCnqde8AtXKuMpVG1JAlWLa4tr0A1b4KLwAdVZAngfhoVnst3j3mLQim2QKBgQDNSYUw5UEb5Iz30KkJMehnXzUqvgRQDbqxCWYeQSRFRZVBzDezsz9bjxsyI1AvokixotxG/i7vks5JS3cpbKndoEosQFNkejruc7ZACE+IP6Tk61n6WUcber98vI9TmYfrDL/CdpvZiTVESwwdfm1kbPMtbDtaWCApOXiwr9EHoQKBgHod2Yj5EyFfnKh2KMmHmZK/JibAcuc8p23u3rEfrqpKF8VkLJZDFGztVxS36k5dpoN/aGw5jpil8k9eOEPimn+O5EPx3r9T3INFaEnbucKPT/DybI9npHq9MBWlto6z3PUBz9utXglYb9I0c1v7+wLM2YPgM6vsHNbmVV6A0HwxAoGBAOQhg2KilaGRSB9l3vXmYEMqXCX4nbLl+nxEdATKXkimFK3+WtCo/8pm09ZEFY+M++bIgje9l300j3htGJVxEdJzJwVDQKU9fmFwLz6phX/DboOPBOp3wnKO3XXeiCy3dqy63hlqa4XWtuz3TXP2wYJCgRweKV5vP+YxLj4opG5b", "json",
          charset, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj0S5tRmiFLd7jGkgtC2yA9YnDpLJVO0jijFeFLCd+px3jz/EAiOqsAM0ETcAFmEnUryGo0GJWg8VxygnNHxQyXB8R2/hlpeVo3Xtl8Pn6z36UIFkS47DOFhT37tD4CzlSzPqZA45Bw04nTYQEFmBTXLwaTiNgsq2vWlwIE/xH6Eh4w+NUQTK5BmB7Yh/u7WSI46Fnj866weJlevblLBCYE2Ja6uf1iBo1qYKyo+HACSqcVWxDx1/992kgpyLGw6QE5UrUdmLxXfus4zwhZTVsXp+ih/Tpiw4bZ8SB7sYv6sWFY0oyJ1odJaV009+24tF32kmtVfcuCzrcR+OnVtxXwIDAQAB", signType);

      //发送API请求
      String orderId = FormatDateTime.currentRandomTimeString();
      AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
      request.setNotifyUrl("http://rongmeitech.com/account");
      request.setReturnUrl("http://rongmeitech.com/account");
      request.setBizContent(
          "{\"out_trade_no\":\"" + orderId + "\"," + "\"total_amount\":\"" + parameters
              .getTotalAmount() + "\"," + "\"subject\":\"" + parameters.getSubject() + "\","
              + "\"product_code\":\"QUICK_WAP_WAY\"}");
      return new SuccessResponse(alipayClient.pageExecute(request).getBody());
    } catch (AlipayApiException e) {
      e.printStackTrace();
      throw new SystemException();
    }
  }
}
