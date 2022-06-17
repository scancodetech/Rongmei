package com.rongmei.response.auction;

import com.rongmei.response.Response;
import java.util.List;

public class SaleGetResponse extends Response {

  private List<SaleItem> saleItems;
  private int total;

  public SaleGetResponse() {
  }

  public SaleGetResponse(List<SaleItem> saleItems, int total) {
    this.saleItems = saleItems;
    this.total = total;
  }

  public List<SaleItem> getSaleItems() {
    return saleItems;
  }

  public void setSaleItems(List<SaleItem> saleItems) {
    this.saleItems = saleItems;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
