package com.rongmei.response.blindboxnft;

import com.rongmei.response.Response;
import java.util.List;

public class BlindBoxNFTGetResponse extends Response {

  private List<BlindBoxNFTItem> blindBoxNFTItemList;

  public BlindBoxNFTGetResponse() {
  }

  public BlindBoxNFTGetResponse(
      List<BlindBoxNFTItem> blindBoxNFTItemList) {
    this.blindBoxNFTItemList = blindBoxNFTItemList;
  }

  public List<BlindBoxNFTItem> getBlindBoxNFTItemList() {
    return blindBoxNFTItemList;
  }

  public void setBlindBoxNFTItemList(
      List<BlindBoxNFTItem> blindBoxNFTItemList) {
    this.blindBoxNFTItemList = blindBoxNFTItemList;
  }
}
