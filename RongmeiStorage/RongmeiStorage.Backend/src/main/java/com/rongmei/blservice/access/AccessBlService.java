package com.rongmei.blservice.access;

import com.rongmei.parameters.access.AccessUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.access.AccessGetResponse;

public interface AccessBlService {

  SuccessResponse updateAccess(AccessUpdateParameters parameters);

  AccessGetResponse getAccess();
}
