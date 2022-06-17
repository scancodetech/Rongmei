package com.rongmei.entity.auction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auction_transaction")
public class AuctionTransaction {
  @Id
  @Column(name = "transaction_id" ,columnDefinition = "varchar(200)")
  private String transactionId;
  @Column(name = "tokenId" ,columnDefinition = "varchar(100)")
  private String tokenId;
  @Column(name = "name"  ,columnDefinition = "varchar(100)")
  private String name;
  @Column(name = "createTime")
  private long createTime;
  @Column(name = "username"  ,columnDefinition = "varchar(100)")
  private String username;
  @Column(name = "eth")
  private double eth;
  @Column(name = "nft")
  private int nft;

  public AuctionTransaction() {
  }

  public AuctionTransaction(String transactionId, String tokenId, String name, long createTime,
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

  public double getEth() {
    return eth;
  }

  public void setEth(double eth) {
    this.eth = eth;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getNft() {
    return nft;
  }

  public void setNft(int nft) {
    this.nft = nft;
  }
}
