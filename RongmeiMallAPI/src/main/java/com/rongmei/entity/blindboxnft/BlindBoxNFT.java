package com.rongmei.entity.blindboxnft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blind_box_nft")
public class BlindBoxNFT {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "url")
  private String url;
  @Column(name = "price")
  private int price;
  @Column(name = "description")
  private String description;  //盒蛋简介
  @Column(name = "author")
  private String author = "";
  @Column(name = "owner")
  private String owner = "";
  @Column(name = "tokenId")
  private String tokenId;
  @Column(name = "theme")
  private String theme;
  @Column(name = "classification")
  private String classification;
  @Column(name = "chain_tx_id", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String chainTxId;
  @Column(name = "confirmation_letter_url", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String confirmationLetterUrl;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;


  @Column(name = "resourceInfo_url")
  private String resourceInfoURL;//源文件
  @Column(name = "limit_number")
  private int limitNumber;//限量数
  @Column(name = "wallet_address")
  private String walletAddress;//钱包地址
  @Column(name = "box_egg_id")
  private int boxEggId;//发行多分盒蛋信息的id，在数据库的行数，相当于一款发行了很多份。

  public BlindBoxNFT() {
  }

  public BlindBoxNFT(String name, String url, int price, String description,
      String author, String owner, String tokenId, String theme, String classification,
      String chainTxId, String confirmationLetterUrl, long createTime, long updateTime,
      boolean isActive) {
    this.name = name;
    this.url = url;
    this.price = price;
    this.description = description;
    this.author = author;
    this.owner = owner;
    this.tokenId = tokenId;
    this.theme = theme;
    this.classification = classification;
    this.chainTxId = chainTxId;
    this.confirmationLetterUrl = confirmationLetterUrl;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
  }

  public BlindBoxNFT( String name, String url, int price, String description, String author, String owner, String tokenId, String theme, String classification, String chainTxId, String confirmationLetterUrl, long createTime, long updateTime, boolean isActive, String resourceInfoURL, int limitNumber, String walletAddress, int boxEggId) {
    this.name = name;
    this.url = url;
    this.price = price;
    this.description = description;
    this.author = author;
    this.owner = owner;
    this.tokenId = tokenId;
    this.theme = theme;
    this.classification = classification;
    this.chainTxId = chainTxId;
    this.confirmationLetterUrl = confirmationLetterUrl;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.resourceInfoURL = resourceInfoURL;
    this.limitNumber = limitNumber;
    this.walletAddress = walletAddress;
    this.boxEggId = boxEggId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public String getChainTxId() {
    return chainTxId;
  }

  public void setChainTxId(String chainTxId) {
    this.chainTxId = chainTxId;
  }

  public String getConfirmationLetterUrl() {
    return confirmationLetterUrl;
  }

  public void setConfirmationLetterUrl(String confirmationLetterUrl) {
    this.confirmationLetterUrl = confirmationLetterUrl;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getResourceInfoURL() {
    return resourceInfoURL;
  }

  public void setResourceInfoURL(String resourceInfoURL) {
    this.resourceInfoURL = resourceInfoURL;
  }

  public int getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(int limitNumber) {
    this.limitNumber = limitNumber;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  public void setWalletAddress(String walletAddress) {
    this.walletAddress = walletAddress;
  }

  public int getBoxEggId() {
    return boxEggId;
  }

  public void setBoxEggId(int boxEggId) {
    this.boxEggId = boxEggId;
  }
}
