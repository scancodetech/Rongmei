package com.rongmei.response.image;

import com.rongmei.response.Response;

public class ImageResponse extends Response {

  private String url;

  public ImageResponse() {
  }

  public ImageResponse(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
