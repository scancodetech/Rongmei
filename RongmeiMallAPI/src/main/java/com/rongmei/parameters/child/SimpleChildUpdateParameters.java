package com.rongmei.parameters.child;

import java.util.List;

public class SimpleChildUpdateParameters {

  private int id;
  private String coverUrl;
  private String content;
  private List<String> topics;

  public SimpleChildUpdateParameters() {
  }

  public SimpleChildUpdateParameters(int id, String coverUrl, String content,
      List<String> topics) {
    this.id = id;
    this.coverUrl = coverUrl;
    this.content = content;
    this.topics = topics;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }
}