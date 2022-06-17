package com.rongmei.blservice.blindboxnft;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderCancelParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderFinishParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxSaleUpdateParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxThemeQueueParameter;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTSaleGetResponse;
import com.rongmei.response.blindboxnft.BlindBoxSaleDetailResponse;
import com.rongmei.response.blindboxnft.BlindBoxSaleStatisticsResponse;

public interface BlindBoxSaleBlService {

  SuccessResponse updateSale(BlindBoxSaleUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteSale(int saleId) throws ThingIdDoesNotExistException;

  BlindBoxSaleDetailResponse getBlindBoxSaleDetail(long id) throws ThingIdDoesNotExistException;

  BlindBoxNFTSaleGetResponse getRecommendBlindBoxSaleList(String username, int page, int limit);

  SuccessResponse orderQueueBlindBoxTheme(BlindBoxThemeQueueParameter parameter, String username);

  SuccessResponse orderFinishBlindBox(BlindBoxOrderFinishParameter parameter, String username)
      throws ThingIdDoesNotExistException;

  BlindBoxNFTSaleGetResponse getBlindBoxSaleList(String theme, String classification);

  SuccessResponse orderCancelBlindBox(BlindBoxOrderCancelParameter parameter, String username)
      throws ThingIdDoesNotExistException;

  BlindBoxNFTSaleGetResponse getMineBlindBoxSaleList(int draftStatus);

  BlindBoxSaleStatisticsResponse getUserBlindBoxSaleStatistics(String username, long startTime,
      long endTime);
}
