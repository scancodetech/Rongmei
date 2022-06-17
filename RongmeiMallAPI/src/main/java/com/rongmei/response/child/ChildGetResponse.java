package com.rongmei.response.child;

import com.rongmei.response.Response;
import java.util.List;

public class ChildGetResponse extends Response {

  private List<ChildItem> childItems;

  public ChildGetResponse() {
  }

  public ChildGetResponse(List<ChildItem> childItems) {
    this.childItems = childItems;
  }

  public List<ChildItem> getChildItems() {
    return childItems;
  }

  public void setChildItems(List<ChildItem> childItems) {
    this.childItems = childItems;
  }
}
