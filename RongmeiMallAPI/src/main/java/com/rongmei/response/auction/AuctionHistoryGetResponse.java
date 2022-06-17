package com.rongmei.response.auction;

import com.rongmei.response.Response;
import java.util.List;

public class AuctionHistoryGetResponse extends Response {

  private List<AuctionHistory> auctionHistories;

  public AuctionHistoryGetResponse() {
  }

  public AuctionHistoryGetResponse(
      List<AuctionHistory> auctionHistories) {
    this.auctionHistories = auctionHistories;
  }

  public List<AuctionHistory> getAuctionHistories() {
    return auctionHistories;
  }

  public void setAuctionHistories(
      List<AuctionHistory> auctionHistories) {
    this.auctionHistories = auctionHistories;
  }
}
