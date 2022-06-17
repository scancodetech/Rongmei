package com.rongmei.blservice.child;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.ChildUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.child.ChildDetailResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.response.child.TopicsGetResponse;

public interface ChildBlService {

  SuccessResponse updateChild(ChildUpdateParameters parameters) throws ThingIdDoesNotExistException;

  ChildDetailResponse getChildDetail(int id) throws ThingIdDoesNotExistException;

  ChildGetResponse getChildren(int page, int limit);

  ChildGetResponse getUserChild(int page, int limit, String username);

  SuccessResponse deleteChild(int id) throws ThingIdDoesNotExistException;
}
