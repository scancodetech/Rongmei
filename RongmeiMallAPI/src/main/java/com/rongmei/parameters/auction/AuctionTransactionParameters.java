package com.rongmei.parameters.auction;

public class AuctionTransactionParameters {

  private String transactionId;
  private String tokenId;
  private String name;
  private String username;
  private double eth;
  private int nft;

  public AuctionTransactionParameters() {
  }

  public AuctionTransactionParameters(String transactionId, String tokenId, String name,
      String username, double eth, int nft) {
    this.transactionId = transactionId;
    this.tokenId = tokenId;
    this.name = name;
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
