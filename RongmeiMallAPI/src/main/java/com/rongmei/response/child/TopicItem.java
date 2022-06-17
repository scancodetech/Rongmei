package com.rongmei.response.child;

public class TopicItem {

  private String topic;
  private long count;

  public TopicItem() {
  }

  public TopicItem(String topic, long count) {
    this.topic = topic;
    this.count = count;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
