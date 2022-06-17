package com.rongmei.response.boxorder;

import com.rongmei.response.Response;
import java.util.List;

public class BoxOrderGetResponse extends Response {

  private List<BoxOrderItem> boxOrderItemList;
  private long total;

  public BoxOrderGetResponse() {
  }

  public BoxOrderGetResponse(
      List<BoxOrderItem> boxOrderItemList, long total) {
    this.boxOrderItemList = boxOrderItemList;
    this.total = total;
  }

  public List<BoxOrderItem> getBoxOrderItemList() {
    return boxOrderItemList;
  }

  public void setBoxOrderItemList(
      List<BoxOrderItem> boxOrderItemList) {
    this.boxOrderItemList = boxOrderItemList;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
