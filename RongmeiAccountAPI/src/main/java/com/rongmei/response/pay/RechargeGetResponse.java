package com.rongmei.response.pay;

import com.rongmei.response.Response;
import java.util.List;

public class RechargeGetResponse extends Response {

  private List<RechargeItem> rechargeItems;

  public RechargeGetResponse() {
  }

  public RechargeGetResponse(List<RechargeItem> rechargeItems) {
    this.rechargeItems = rechargeItems;
  }

  public List<RechargeItem> getRechargeItems() {
    return rechargeItems;
  }

  public void setRechargeItems(List<RechargeItem> rechargeItems) {
    this.rechargeItems = rechargeItems;
  }
}
