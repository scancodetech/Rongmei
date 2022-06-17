package com.rongmei.response.auction;

import com.rongmei.response.Response;
import java.util.List;

public class AuctionTagGetResponse extends Response {

  private List<TagHotItem> tagHotItems;

  public AuctionTagGetResponse() {
  }

  public AuctionTagGetResponse(List<TagHotItem> tagHotItems) {
    this.tagHotItems = tagHotItems;
  }

  public List<TagHotItem> getTagHotItems() {
    return tagHotItems;
  }

  public void setTagHotItems(List<TagHotItem> tagHotItems) {
    this.tagHotItems = tagHotItems;
  }
}
