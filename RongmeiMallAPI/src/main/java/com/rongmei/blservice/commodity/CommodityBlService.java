package com.rongmei.blservice.commodity;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.commodity.CommodityQueryParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParamters2;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.commodity.CommodityAuthorDetailResponse;
import com.rongmei.response.commodity.CommodityDetailResponse;
import com.rongmei.response.commodity.CommodityGetResponse;
import com.rongmei.response.commodity.CommoditySaleStatisticsResponse;

import java.util.List;

public interface CommodityBlService {

  CommodityDetailResponse updateCommodity(CommodityUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteCommodity(int commodityId);

  CommodityGetResponse getCommodities(CommodityQueryParameters parameters);

  CommodityDetailResponse getCommodity(int commodityId) throws ThingIdDoesNotExistException;

  CommodityGetResponse getUserGroupCommodities(int userGroupId);

  CommodityGetResponse getRecommendCommodities(int count);

  CommodityAuthorDetailResponse getCommodityAuthor(int commodityId)
      throws ThingIdDoesNotExistException;

  CommodityGetResponse getTopicCommodities(String key, int offset, int limit);

  /**
   * @param status   0:已购,1:喜欢
   * @param username
   * @return
   */
  CommodityGetResponse getFavoritesCommodities(int favoritesId, int status,
      String username);

  /**
   * @param status 0:已购,1:喜欢
   */
  SuccessResponse moveFavoritesCommodity(int favoritesId, int commodityId, int status);

  /**
   * @param status //0:审核中 1:未通过 2:已通过
   * @return
   */
  CommodityGetResponse getAuthorCommodities(int status, String author);

  CommoditySaleStatisticsResponse getUserStatistics(String username, long startTime, long endTime);

  /**
   * 更新多组素材
   * @param parameters
   * @return
   */
  Response updateCommodityByGroup(List<CommodityUpdateParameters> parameters);
}
