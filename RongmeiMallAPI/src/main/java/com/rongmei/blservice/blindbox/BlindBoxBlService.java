package com.rongmei.blservice.blindbox;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindbox.BlindBoxUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindbox.BlindBoxDetailResponse;
import com.rongmei.response.blindbox.BlindBoxGetResponse;

public interface BlindBoxBlService {

  SuccessResponse updateBlindBox(BlindBoxUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  BlindBoxDetailResponse getBlindBoxDetail(long id) throws ThingIdDoesNotExistException;

  BlindBoxGetResponse getBlindBoxList(String theme, String classification);

  BlindBoxGetResponse getRecommendBlindBoxList(String username);
}
