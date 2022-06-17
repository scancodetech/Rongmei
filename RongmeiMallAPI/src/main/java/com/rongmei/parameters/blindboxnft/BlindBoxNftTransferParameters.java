package com.rongmei.parameters.blindboxnft;

public class BlindBoxNftTransferParameters {

  private String tokenId;
  private String formUsername;
  private String toUsername;

  public BlindBoxNftTransferParameters() {
  }

  public BlindBoxNftTransferParameters(String tokenId, String formUsername,
      String toUsername) {
    this.tokenId = tokenId;
    this.formUsername = formUsername;
    this.toUsername = toUsername;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getFormUsername() {
    return formUsername;
  }

  public void setFormUsername(String formUsername) {
    this.formUsername = formUsername;
  }

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }
}
