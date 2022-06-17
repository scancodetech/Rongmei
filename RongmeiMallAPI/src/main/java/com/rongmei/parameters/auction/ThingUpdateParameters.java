package com.rongmei.parameters.auction;

import java.util.List;

public class ThingUpdateParameters {

  private int id;
  private String name;
  private String url;
  private double price;
  private String description;
  private String tokenId;
  private List<String> tags;
  private String chainTxId;
  private String confirmationLetterUrl;

  public ThingUpdateParameters() {
  }

  public ThingUpdateParameters(int id, String name, String url, double price,
      String description, String tokenId, List<String> tags, String chainTxId,
      String confirmationLetterUrl) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.price = price;
    this.description = description;
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
