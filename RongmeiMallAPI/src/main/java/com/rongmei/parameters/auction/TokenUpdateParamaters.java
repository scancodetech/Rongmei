package com.rongmei.parameters.auction;

public class TokenUpdateParamaters {

  private long tokenId;
  private String value;

  public TokenUpdateParamaters() {
  }

  public TokenUpdateParamaters(long tokenId, String value) {
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
