package com.rongmei.response.commodity;

import com.rongmei.response.Response;
import java.util.List;

public class CommodityGetResponse extends Response {

  private List<CommodityItem> commodities;
  private int total;

  public CommodityGetResponse() {
  }

  public CommodityGetResponse(List<CommodityItem> commodities, int total) {
    this.commodities = commodities;
    this.total = total;
  }

  public List<CommodityItem> getCommodities() {
    return commodities;
  }

  public void setCommodities(List<CommodityItem> commodities) {
    this.commodities = commodities;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
