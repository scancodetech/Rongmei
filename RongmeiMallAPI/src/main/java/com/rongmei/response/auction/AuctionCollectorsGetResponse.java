package com.rongmei.response.auction;

import com.rongmei.response.Response;
import com.rongmei.response.user.UserInfoDetail;
import java.util.List;

public class AuctionCollectorsGetResponse extends Response {

  List<UserInfoDetail> auctionCollectorItems;

  public AuctionCollectorsGetResponse() {
  }

  public AuctionCollectorsGetResponse(
      List<UserInfoDetail> auctionCollectorItems) {
    this.auctionCollectorItems = auctionCollectorItems;
  }

  public List<UserInfoDetail> getAuctionCollectorItems() {
    return auctionCollectorItems;
  }

  public void setAuctionCollectorItems(
      List<UserInfoDetail> auctionCollectorItems) {
    this.auctionCollectorItems = auctionCollectorItems;
  }
}
