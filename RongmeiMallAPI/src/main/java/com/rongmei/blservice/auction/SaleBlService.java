package com.rongmei.blservice.auction;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.SaleQueryParameters;
import com.rongmei.parameters.auction.SaleQueryParmeters;
import com.rongmei.parameters.auction.SaleUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.AuctionTagGetResponse;
import com.rongmei.response.auction.SaleDetailResponse;
import com.rongmei.response.auction.SaleGetResponse;

public interface SaleBlService {

  SaleGetResponse getSales(SaleQueryParameters parameters);

  SaleDetailResponse getSale(int saleId, String username) throws ThingIdDoesNotExistException;

  SuccessResponse updateSale(SaleUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteSale(int saleId) throws ThingIdDoesNotExistException;

  SaleGetResponse getMineSales(int draftStatus);

  SaleGetResponse getSalesParticipate(String username);

  SaleGetResponse getCurrentSale(int limit, int offset);

  double getCurrentSalePrice(int saleId);

  SaleGetResponse getTopicSales(String key, int offset, int limit);

  AuctionTagGetResponse getTopTags(long startTime, long endTime);

  Object getFinanceList(SaleQueryParmeters saleQueryParmeters) throws Exception;
}