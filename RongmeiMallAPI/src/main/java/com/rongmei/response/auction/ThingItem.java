package com.rongmei.response.auction;

import java.util.List;

public class ThingItem {

  private int id;
  private String name;
  private String url;
  private double price;
  private String description;
  private long createTime;
  private String author;
  private String owner;
  private String tokenId;
  private List<String> tags;
  private String chainTxId;
  private String confirmationLetterUrl;

  public ThingItem() {
  }

  public ThingItem(int id, String name, String url, double price, String description,
      long createTime,
      String author, String owner, String tokenId, List<String> tags, String chainTxId,
      String confirmationLetterUrl) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.price = price;
    this.description = description;
    this.createTime = createTime;
    this.author = author;
    this.owner = owner;
    this.tokenId = tokenId;
    this.tags = tags;
    this.chainTxId = chainTxId;
    this.confirmationLetterUrl = confirmationLetterUrl;
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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
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
}
