package com.rongmei.parameters.child;

public class TopicUpdateParameters {

  private String topic;
  private String coverUrl;
  private String description;

  public TopicUpdateParameters() {
  }

  public TopicUpdateParameters(String topic, String coverUrl, String description) {
    this.topic = topic;
    this.coverUrl = coverUrl;
    this.description = description;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}