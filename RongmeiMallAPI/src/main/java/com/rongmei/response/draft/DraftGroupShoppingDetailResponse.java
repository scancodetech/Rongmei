package com.rongmei.response.draft;

import com.rongmei.response.Response;
import java.util.List;

public class DraftGroupShoppingDetailResponse extends Response {

  private int id;
  private String title;
  private long price;
  private String coverUrl;
  private List<String> tags;
  private String information;
  private String description;
  private String comment;
  private long startTime;
  private long endTime;
  private long createTime;
  private long updateTime;
  private int draftStatus;
  private String msg;

  public DraftGroupShoppingDetailResponse() {
  }

  public DraftGroupShoppingDetailResponse(int id, String title, long price, String coverUrl,
      List<String> tags, String information, String description, String comment, long startTime,
      long endTime, long createTime, long updateTime, int draftStatus, String msg) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.coverUrl = coverUrl;
    this.tags = tags;
    this.information = information;
    this.description = description;
    this.comment = comment;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createTime = createTime;
    this.updateTime = updateTime;
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

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
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

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
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
