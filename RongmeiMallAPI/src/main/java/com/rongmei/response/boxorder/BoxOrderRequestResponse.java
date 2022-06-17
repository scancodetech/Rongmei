package com.rongmei.response.boxorder;

import com.rongmei.response.Response;
import java.util.List;

public class BoxOrderRequestResponse extends Response {

  private List<BoxOrderRequestItem> boxOrderRequestItemList;

  public BoxOrderRequestResponse() {
  }

  public BoxOrderRequestResponse(
      List<BoxOrderRequestItem> boxOrderRequestItemList) {
    this.boxOrderRequestItemList = boxOrderRequestItemList;
  }

  public List<BoxOrderRequestItem> getBoxOrderRequestItemList() {
    return boxOrderRequestItemList;
  }

  public void setBoxOrderRequestItemList(
      List<BoxOrderRequestItem> boxOrderRequestItemList) {
    this.boxOrderRequestItemList = boxOrderRequestItemList;
  }
}
