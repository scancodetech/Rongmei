package com.rongmei.response.child;

import com.rongmei.response.Response;
import java.util.List;

public class SimpleChildGetResponse extends Response {

  private List<SimpleChildItem> simpleChildItemList;
  private long total;

  public SimpleChildGetResponse() {
  }

  public SimpleChildGetResponse(
      List<SimpleChildItem> simpleChildItemList, long total) {
    this.simpleChildItemList = simpleChildItemList;
    this.total = total;
  }

  public List<SimpleChildItem> getSimpleChildItemList() {
    return simpleChildItemList;
  }

  public void setSimpleChildItemList(
      List<SimpleChildItem> simpleChildItemList) {
    this.simpleChildItemList = simpleChildItemList;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
