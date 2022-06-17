package com.rongmei.response.child;

import com.rongmei.response.Response;
import java.util.List;

public class TopicsGetResponse extends Response {

  private List<TopicItem> topicItems;
  private long count;

  public TopicsGetResponse() {
  }

  public TopicsGetResponse(List<TopicItem> topicItems, long count) {
    this.topicItems = topicItems;
    this.count = count;
  }

  public List<TopicItem> getTopicItems() {
    return topicItems;
  }

  public void setTopicItems(List<TopicItem> topicItems) {
    this.topicItems = topicItems;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
