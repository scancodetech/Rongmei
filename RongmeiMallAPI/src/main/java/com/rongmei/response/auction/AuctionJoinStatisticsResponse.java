package com.rongmei.response.auction;

import com.rongmei.response.Response;

public class AuctionJoinStatisticsResponse extends Response {

  int count;

  public AuctionJoinStatisticsResponse() {
  }

  public AuctionJoinStatisticsResponse(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
