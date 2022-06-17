package com.rongmei.response.draft;

import com.rongmei.response.Response;
import com.rongmei.response.commodity.CommodityItem;
import java.util.List;

public class DraftCommodityDetailResponse extends Response {

  private int id;
  private String title;
  private long largePrice;
  private String coverUrl;
  private List<String> tags;
  private String contentUrl;
  private String description;
  private String signingInfo;
  private String extra;
  private int creatorUserGroupId;
  private String downloadUrl;
  private boolean isExclusive;
  private String author;
  private int draftStatus;
  private String msg;

  public DraftCommodityDetailResponse() {
  }

  public DraftCommodityDetailResponse(int id, String title, long largePrice,
      String coverUrl, List<String> tags, String contentUrl, String description,
      String signingInfo, String extra, int creatorUserGroupId, String downloadUrl,
      boolean isExclusive, String author, int draftStatus, String msg) {
    this.id = id;
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
    this.isExclusive = isExclusive;
    this.author = author;
    this.draftStatus = draftStatus;
    this.msg = msg;
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

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
