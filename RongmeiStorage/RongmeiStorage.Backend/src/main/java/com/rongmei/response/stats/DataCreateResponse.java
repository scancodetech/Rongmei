package com.rongmei.response.stats;

import com.rongmei.response.Response;

public class DataCreateResponse extends Response {

  private String txHash;

  public DataCreateResponse() {
  }

  public DataCreateResponse(String txHash) {
    this.txHash = txHash;
  }

  public String getTxHash() {
    return txHash;
  }

  public void setTxHash(String txHash) {
    this.txHash = txHash;
  }
}
