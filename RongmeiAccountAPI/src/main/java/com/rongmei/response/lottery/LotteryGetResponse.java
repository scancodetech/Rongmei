package com.rongmei.response.lottery;

import com.rongmei.response.Response;
import java.util.List;

public class LotteryGetResponse extends Response {

  private List<LotteryItem> lotteryItemList;

  public LotteryGetResponse() {
  }

  public LotteryGetResponse(List<LotteryItem> lotteryItemList) {
    this.lotteryItemList = lotteryItemList;
  }

  public List<LotteryItem> getLotteryItemList() {
    return lotteryItemList;
  }

  public void setLotteryItemList(List<LotteryItem> lotteryItemList) {
    this.lotteryItemList = lotteryItemList;
  }
}
