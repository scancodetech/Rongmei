package com.rongmei.response.recommend;

import com.rongmei.response.Response;
import java.util.List;

public class RecommendGetResponse extends Response {

  private List<RecommendItem> recommendItemList;
  private long total;

  public RecommendGetResponse() {
  }

  public RecommendGetResponse(
      List<RecommendItem> recommendItemList, long total) {
    this.recommendItemList = recommendItemList;
    this.total = total;
  }

  public List<RecommendItem> getRecommendItemList() {
    return recommendItemList;
  }

  public void setRecommendItemList(
      List<RecommendItem> recommendItemList) {
    this.recommendItemList = recommendItemList;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
