package com.rongmei.response.stats;

import com.rongmei.response.Response;

public class DataGetResponse extends Response {

  private String data;

  public DataGetResponse() {
  }

  public DataGetResponse(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
