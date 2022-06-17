package com.rongmei.blservice.distribution;

import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.paramaters.distribution.DistributionParameters;
import com.rongmei.paramaters.distribution.UserDistributionUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.distribution.UserDistributionDetailResponse;

public interface DistributionBlService {

  UserDistributionDetailResponse getUserDistribution(String username)
      throws UsernameDoesNotFoundException;

  SuccessResponse distribution(DistributionParameters parameters)
      throws UsernameDoesNotFoundException;

  SuccessResponse updateUserDistribution(UserDistributionUpdateParameters parameters);
}
