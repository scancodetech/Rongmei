package com.rongmei.response.draft;

import com.rongmei.response.Response;
import com.rongmei.response.commodity.CommodityItem;
import java.util.List;

public class DraftCommodityGetResponse extends Response {

  private List<CommodityItem> commodityItemList;

  public DraftCommodityGetResponse() {
  }

  public DraftCommodityGetResponse(
      List<CommodityItem> commodityItemList) {
    this.commodityItemList = commodityItemList;
  }

  public List<CommodityItem> getCommodityItemList() {
    return commodityItemList;
  }

  public void setCommodityItemList(
      List<CommodityItem> commodityItemList) {
    this.commodityItemList = commodityItemList;
  }
}
