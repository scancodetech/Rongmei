package com.rongmei.response.blindboxnft;

import com.rongmei.response.Response;
import java.util.List;

public class BlindBoxNFTSaleGetResponse extends Response {

  public List<BlindBoxSaleItem> blindBoxSaleItemList;

  public BlindBoxNFTSaleGetResponse() {
  }

  public BlindBoxNFTSaleGetResponse(
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
