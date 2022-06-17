package com.rongmei.blservice.platform;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.platform.PlatformUpdateParameters;
import com.rongmei.response.platform.PlatformDetailResponse;
import com.rongmei.response.platform.PlatformListResponse;

public interface PlatformBlService {

  PlatformListResponse getPlatforms();

  PlatformDetailResponse getPlatformDetail(int platformId) throws ThingIdDoesNotExistException;

  PlatformDetailResponse updatePlatform(PlatformUpdateParameters parameters);
}
