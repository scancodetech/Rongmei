package com.rongmei.blservice.relation;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.relation.FavoritesUpdateParameters;
import com.rongmei.parameters.relation.LikeRelationUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.relation.FavoritesGetResponse;
import com.rongmei.response.relation.LikeGetResponse;
import com.rongmei.response.relation.LikeStatisticsResponse;

public interface RelationBlService {

  SuccessResponse updateFavorites(FavoritesUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteFavorites(long id) throws ThingIdDoesNotExistException;

  FavoritesGetResponse getFavorites(String username);

  SuccessResponse postLike(LikeRelationUpdateParameters parameters, String username);

  /**
   * @param relationId
   * @param type       0:sale 1:commodity 2:blindBox
   * @param username
   * @return
   */
  LikeGetResponse getLike(int relationId, int type, String username);

  LikeStatisticsResponse getLikeUserStatistics(int type, String username, long startTime,
      long endTime);
}
