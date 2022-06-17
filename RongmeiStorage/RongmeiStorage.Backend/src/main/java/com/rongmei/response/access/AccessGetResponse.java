package com.rongmei.response.access;

import com.rongmei.response.Response;
import java.util.List;

public class AccessGetResponse extends Response {

  private List<AccessItem> accessItemList;

  public AccessGetResponse() {
  }

  public AccessGetResponse(List<AccessItem> accessItemList) {
    this.accessItemList = accessItemList;
  }

  public List<AccessItem> getAccessItemList() {
    return accessItemList;
  }

  public void setAccessItemList(List<AccessItem> accessItemList) {
    this.accessItemList = accessItemList;
  }
}
