package com.rongmei.parameters.boxorder;

public class BoxOrderResultCoverUrlSubmitParameters {

  private long id;
  private String coverUrl;

  public BoxOrderResultCoverUrlSubmitParameters() {
  }

  public BoxOrderResultCoverUrlSubmitParameters(long id, String coverUrl) {
    this.id = id;
    this.coverUrl = coverUrl;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }
}
