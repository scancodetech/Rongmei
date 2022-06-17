package com.rongmei.blservice.stats;

import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.stats.StatsCreateParameters;
import com.rongmei.response.stats.DataCreateResponse;
import com.rongmei.response.stats.DataGetResponse;
import com.rongmei.response.stats.HomeStatsGetResponse;
import com.rongmei.response.stats.StatsGetResponse;

public interface StatsBlService {

  DataCreateResponse createStats(StatsCreateParameters parameters)
      throws UsernameDoesNotFoundException;

  StatsGetResponse getStats();

  HomeStatsGetResponse getHomeStats();

  DataGetResponse getData(String txHash);
}
