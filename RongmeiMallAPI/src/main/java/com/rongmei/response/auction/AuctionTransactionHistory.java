package com.rongmei.response.auction;

public class AuctionTransactionHistory {

  private String transactionId;
  private String tokenId;
  private String name;
  private long createTime;
  private String username;
  private double eth;
  private int nft;

  public AuctionTransactionHistory() {
  }

  public AuctionTransactionHistory(String transactionId, String tokenId, String name,
      long createTime,
      String username, double eth, int nft) {
    this.transactionId = transactionId;
    this.tokenId = tokenId;
    this.name = name;
    this.createTime = createTime;
    this.username = username;
    this.eth = eth;
    this.nft = nft;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getEth() {
    return eth;
  }

  public void setEth(double eth) {
    this.eth = eth;
  }

  public int getNft() {
    return nft;
  }

  public void setNft(int nft) {
    this.nft = nft;
  }
}
