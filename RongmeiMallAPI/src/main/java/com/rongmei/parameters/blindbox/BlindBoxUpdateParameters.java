package com.rongmei.parameters.blindbox;

public class BlindBoxUpdateParameters {

  private long id;
  private String coverUrl;
  private String name;
  private int price;
  private String theme;
  private String classification;
  private int totalCount;

  public BlindBoxUpdateParameters() {
  }

  public BlindBoxUpdateParameters(long id, String coverUrl, String name, int price,
      String theme, String classification, int totalCount) {
    this.id = id;
    this.coverUrl = coverUrl;
    this.name = name;
    this.price = price;
    this.theme = theme;
    this.classification = classification;
    this.totalCount = totalCount;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
