package com.rongmei.response.auction;

import com.rongmei.response.Response;

public class TokenResponse extends Response {

  private long tokenId;
  private String value;

  public TokenResponse() {
  }

  public TokenResponse(long tokenId, String value) {
    this.tokenId = tokenId;
    this.value = value;
  }

  public long getTokenId() {
    return tokenId;
  }

  public void setTokenId(long tokenId) {
    this.tokenId = tokenId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
