package com.rongmei.response.draft;

import com.rongmei.response.Response;
import com.rongmei.response.blindboxnft.BlindBoxSaleItem;
import java.util.List;

public class DraftBlindBoxSaleDetailResponse extends Response {

  private int saleId;
  private int blindBoxNftId;
  private String name;
  private String url;
  private int price;
  private String description;
  private String author;
  private String owner;
  private String tokenId;
  private String theme;
  private String classification;
  private String chainTxId;
  private String confirmationLetterUrl;
  private long createTime;
  private long updateTime;
  private boolean isActive;
  private int draftStatus;
  private String msg;

  public DraftBlindBoxSaleDetailResponse() {
  }

  public DraftBlindBoxSaleDetailResponse(int saleId, int blindBoxNftId, String name,
      String url, int price, String description, String author, String owner,
      String tokenId, String theme, String classification, String chainTxId,
      String confirmationLetterUrl, long createTime, long updateTime, boolean isActive,
      int draftStatus, String msg) {
    this.saleId = saleId;
    this.blindBoxNftId = blindBoxNftId;
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
    this.draftStatus = draftStatus;
    this.msg = msg;
  }

  public int getSaleId() {
    return saleId;
  }

  public void setSaleId(int saleId) {
    this.saleId = saleId;
  }

  public int getBlindBoxNftId() {
    return blindBoxNftId;
  }

  public void setBlindBoxNftId(int blindBoxNftId) {
    this.blindBoxNftId = blindBoxNftId;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
