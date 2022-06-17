package com.rongmei.response.boxorder;

import com.rongmei.response.Response;
import java.util.List;

public class BoxOrderShareGetResponse extends Response {

  private List<BoxOrderShareItem> boxOrderShareItemList;

  public BoxOrderShareGetResponse() {
  }

  public BoxOrderShareGetResponse(
      List<BoxOrderShareItem> boxOrderShareItemList) {
    this.boxOrderShareItemList = boxOrderShareItemList;
  }

  public List<BoxOrderShareItem> getBoxOrderShareItemList() {
    return boxOrderShareItemList;
  }

  public void setBoxOrderShareItemList(
      List<BoxOrderShareItem> boxOrderShareItemList) {
    this.boxOrderShareItemList = boxOrderShareItemList;
  }
}
