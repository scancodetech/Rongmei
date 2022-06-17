package com.rongmei.response.blindbox;

import com.rongmei.response.Response;
import java.util.List;

public class BlindBoxGetResponse extends Response {

  private List<BlindBoxItem> blindBoxItemList;

  public BlindBoxGetResponse() {
  }

  public BlindBoxGetResponse(
      List<BlindBoxItem> blindBoxItemList) {
    this.blindBoxItemList = blindBoxItemList;
  }

  public List<BlindBoxItem> getBlindBoxItemList() {
    return blindBoxItemList;
  }

  public void setBlindBoxItemList(
      List<BlindBoxItem> blindBoxItemList) {
    this.blindBoxItemList = blindBoxItemList;
  }
}
