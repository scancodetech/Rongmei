package com.rongmei.response.draft;

import com.rongmei.response.Response;
import com.rongmei.response.auction.SaleItem;
import java.util.List;

public class DraftSaleGetResponse extends Response {

  private List<SaleItem> saleItemList;

  public DraftSaleGetResponse(List<SaleItem> saleItemList) {
    this.saleItemList = saleItemList;
  }

  public List<SaleItem> getSaleItemList() {
    return saleItemList;
  }

  public void setSaleItemList(List<SaleItem> saleItemList) {
    this.saleItemList = saleItemList;
  }
}
