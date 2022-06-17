package com.rongmei.response.blindbox;

import com.rongmei.response.Response;
import java.util.List;

public class BlindBoxOrderGetResponse extends Response {

  private List<BlindBoxOrderItem> blindBoxOrderItemList;

  public BlindBoxOrderGetResponse() {
  }

  public BlindBoxOrderGetResponse(
      List<BlindBoxOrderItem> blindBoxOrderItemList) {
    this.blindBoxOrderItemList = blindBoxOrderItemList;
  }

  public List<BlindBoxOrderItem> getBlindBoxOrderItemList() {
    return blindBoxOrderItemList;
  }

  public void setBlindBoxOrderItemList(
      List<BlindBoxOrderItem> blindBoxOrderItemList) {
    this.blindBoxOrderItemList = blindBoxOrderItemList;
  }
}
