package com.rongmei.response.auction;

import com.rongmei.response.Response;

public class AuctionStatisticsResponse extends Response {

  private long thingNum;
  private double ethNum;
  private long saleNum;

  public AuctionStatisticsResponse() {
  }

  public AuctionStatisticsResponse(long thingNum, double ethNum, long saleNum) {
    this.thingNum = thingNum;
    this.ethNum = ethNum;
    this.saleNum = saleNum;
  }

  public long getThingNum() {
    return thingNum;
  }

  public void setThingNum(long thingNum) {
    this.thingNum = thingNum;
  }

  public double getEthNum() {
    return ethNum;
  }

  public void setEthNum(double ethNum) {
    this.ethNum = ethNum;
  }

  public long getSaleNum() {
    return saleNum;
  }

  public void setSaleNum(long saleNum) {
    this.saleNum = saleNum;
  }
}
