package com.rongmei.response.groupshopping;

import com.rongmei.response.Response;
import java.util.List;

public class GroupShoppingGetResponse extends Response {

  private List<GroupShoppingItem> groupShoppingItemList;
  private long total;

  public GroupShoppingGetResponse() {
  }

  public GroupShoppingGetResponse(
      List<GroupShoppingItem> groupShoppingItemList, long total) {
    this.groupShoppingItemList = groupShoppingItemList;
    this.total = total;
  }

  public List<GroupShoppingItem> getGroupShoppingItemList() {
    return groupShoppingItemList;
  }

  public void setGroupShoppingItemList(
      List<GroupShoppingItem> groupShoppingItemList) {
    this.groupShoppingItemList = groupShoppingItemList;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
