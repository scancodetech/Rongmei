package com.rongmei.response.auction;

import com.rongmei.response.Response;
import java.util.List;

public class AuctionTransactionHistoryGetResponse extends Response {

  private List<AuctionTransactionHistory> auctionTransactionHistories;

  public AuctionTransactionHistoryGetResponse() {
  }

  public AuctionTransactionHistoryGetResponse(
      List<AuctionTransactionHistory> auctionTransactionHistories) {
    this.auctionTransactionHistories = auctionTransactionHistories;
  }

  public List<AuctionTransactionHistory> getAuctionTransactionHistories() {
    return auctionTransactionHistories;
  }

  public void setAuctionTransactionHistories(
      List<AuctionTransactionHistory> auctionTransactionHistories) {
    this.auctionTransactionHistories = auctionTransactionHistories;
  }
}
