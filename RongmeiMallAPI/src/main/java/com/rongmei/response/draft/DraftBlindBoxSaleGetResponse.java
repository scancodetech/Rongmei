package com.rongmei.response.draft;

import com.rongmei.response.Response;
import com.rongmei.response.blindboxnft.BlindBoxSaleItem;
import java.util.List;

public class DraftBlindBoxSaleGetResponse extends Response {

  private List<BlindBoxSaleItem> blindBoxSaleItemList;

  public DraftBlindBoxSaleGetResponse() {
  }

  public DraftBlindBoxSaleGetResponse(
      List<BlindBoxSaleItem> blindBoxSaleItemList) {
    this.blindBoxSaleItemList = blindBoxSaleItemList;
  }

  public List<BlindBoxSaleItem> getBlindBoxSaleItemList() {
    return blindBoxSaleItemList;
  }

  public void setBlindBoxSaleItemList(
      List<BlindBoxSaleItem> blindBoxSaleItemList) {
    this.blindBoxSaleItemList = blindBoxSaleItemList;
  }
}
