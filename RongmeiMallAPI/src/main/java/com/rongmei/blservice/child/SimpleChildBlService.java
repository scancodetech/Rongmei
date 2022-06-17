package com.rongmei.blservice.child;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.SimpleChildUpdateParameters;
import com.rongmei.parameters.child.TopicUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.child.SimpleChildGetResponse;
import com.rongmei.response.child.TopicDetailResponse;
import com.rongmei.response.child.TopicsGetResponse;

public interface SimpleChildBlService {

  SuccessResponse updateChild(SimpleChildUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteChild(int id) throws ThingIdDoesNotExistException;

  SimpleChildGetResponse getUserChild(int page, int limit, String username);

  SimpleChildGetResponse getChildren(int page, int limit);

  SimpleChildGetResponse getChildrenByTopic(String topic, int page, int limit);

  TopicsGetResponse getTopTopics(int page, int limit);

  SuccessResponse updateTopic(TopicUpdateParameters parameters);

  TopicDetailResponse getTopicDetail(String topic);
}