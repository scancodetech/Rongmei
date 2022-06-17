package com.rongmei.blservice.auction;

import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.AuctionParamaters;
import com.rongmei.parameters.auction.AuctionTransactionParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.AuctionHistoryGetResponse;
import com.rongmei.response.auction.AuctionJoinStatisticsResponse;
import com.rongmei.response.commodity.CommoditySaleStatisticsResponse;
import com.rongmei.response.auction.AuctionStatisticsResponse;
import com.rongmei.response.auction.AuctionTransactionHistoryGetResponse;
import com.rongmei.response.auction.SaleDetailResponse;

public interface AuctionBlService {

  SaleDetailResponse bid(AuctionParamaters parameters, String username)
      throws ThingIdDoesNotExistException, ParametersErrorException;

  AuctionHistoryGetResponse getAuctionHistory(int saleId);

  AuctionTransactionHistoryGetResponse getAuctionTransactionHistory();

  SuccessResponse addAuctionTransaction(AuctionTransactionParameters parameters);

  AuctionTransactionHistoryGetResponse getCurrentAuctionTransaction(int limit, int offset);

  AuctionStatisticsResponse getStatistics();

  AuctionJoinStatisticsResponse getUserStatistics(String username, long startTime, long endTime);

  Response onePrice(String username, int saleId, int price) throws SystemException;
}
