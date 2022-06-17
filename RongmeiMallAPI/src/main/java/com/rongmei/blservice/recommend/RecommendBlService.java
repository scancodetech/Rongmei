package com.rongmei.blservice.recommend;

import com.rongmei.response.recommend.RecommendGetResponse;

public interface RecommendBlService {

  RecommendGetResponse getRecommendList(int page, int limit, long startFromId, int startFromType,
      String username);
}
