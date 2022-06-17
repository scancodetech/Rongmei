package com.rongmei.parameters.boxorder;

public class BoxOrderResultUrlSubmitParameters {

  private long id;
  private String resultUrl;

  public BoxOrderResultUrlSubmitParameters() {
  }

  public BoxOrderResultUrlSubmitParameters(long id, String resultUrl) {
    this.id = id;
    this.resultUrl = resultUrl;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getResultUrl() {
    return resultUrl;
  }

  public void setResultUrl(String resultUrl) {
    this.resultUrl = resultUrl;
  }
}
