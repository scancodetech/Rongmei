package com.rongmei.entity.auction;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "thing")
public class Thing {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "url")
  private String url;
  @Column(name = "price")
  private double price;
  @Column(name = "description")
  private String description;
  @Column(name = "create_time")
  private long createTime;
  //作者
  @Column(name = "author")
  private String author = "";


  @Column(name = "owner")
  private String owner = "";
  @Column(name = "tokenId")
  private String tokenId;
  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private List<String> tags = new ArrayList<>();
  @Column(name = "chain_tx_id", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String chainTxId;
  @Column(name = "confirmation_letter_url", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String confirmationLetterUrl;//存证地址图片url
  @Column(name = "favorites_id", columnDefinition = "BIGINT DEFAULT 0")
  private int favoritesId;

  public Thing() {
  }

  public Thing(String name, String url, double price, String description, long createTime,
      String author, String owner, String tokenId, List<String> tags, String chainTxId,
      String confirmationLetterUrl, int favoritesId) {
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
    this.favoritesId = favoritesId;
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

  public int getFavoritesId() {
    return favoritesId;
  }

  public void setFavoritesId(int favoritesId) {
    this.favoritesId = favoritesId;
  }
}
