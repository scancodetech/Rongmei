package com.rongmei.entity.commodity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commodity")
public class Commodity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "large_price")
  private long largePrice;
  @Column(name = "cover_url")
  private String coverUrl;
  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private List<String> tags;
  @Column(name = "content_url")
  private String contentUrl;
  @Column(name = "description", columnDefinition = "MEDIUMTEXT")
  private String description;
  @Column(name = "signing_info", columnDefinition = "MEDIUMTEXT")
  private String signingInfo;
  @Column(name = "extra", columnDefinition = "MEDIUMTEXT")
  private String extra;
  @Column(name = "creator_user_group_id", columnDefinition = "BIGINT")
  private int creatorUserGroupId;
  @Column(name = "download_url")
  private String downloadUrl;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_exclusive", columnDefinition = "TINYINT DEFAULT 2")
  private boolean isExclusive;
  @Column(name = "author", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String author;
  @Column(name = "draft_status", columnDefinition = "int default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过

  public Commodity() {
  }

  public Commodity(String title, long largePrice, String coverUrl,
      List<String> tags, String contentUrl, String description, String signingInfo,
      String extra, int creatorUserGroupId, String downloadUrl, long createTime, long updateTime,
      boolean isExclusive, String author, int draftStatus) {
    this.title = title;
    this.largePrice = largePrice;
    this.coverUrl = coverUrl;
    this.tags = tags;
    this.contentUrl = contentUrl;
    this.description = description;
    this.signingInfo = signingInfo;
    this.extra = extra;
    this.creatorUserGroupId = creatorUserGroupId;
    this.downloadUrl = downloadUrl;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isExclusive = isExclusive;
    this.author = author;
      this.draftStatus = draftStatus;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getLargePrice() {
    return largePrice;
  }

  public void setLargePrice(long largePrice) {
    this.largePrice = largePrice;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSigningInfo() {
    return signingInfo;
  }

  public void setSigningInfo(String signingInfo) {
    this.signingInfo = signingInfo;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public int getCreatorUserGroupId() {
    return creatorUserGroupId;
  }

  public void setCreatorUserGroupId(int creatorUserGroupId) {
    this.creatorUserGroupId = creatorUserGroupId;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
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

  public boolean isExclusive() {
    return isExclusive;
  }

  public void setExclusive(boolean exclusive) {
    isExclusive = exclusive;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }
}
