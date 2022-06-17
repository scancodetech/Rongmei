package com.rongmei.entity.groupshopping;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "group_shopping")
public class GroupShopping {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "title")
  private String title;
  @Column(name = "price")
  private long price;
  @Column(name = "cover_url")
  private String coverUrl;
  @Column(name = "tags")
  @ElementCollection(targetClass = String.class)
  private List<String> tags;
  @Column(name = "content_url")
  private String contentUrl;
  @Column(name = "information", columnDefinition = "MEDIUMTEXT")
  private String information;
  @Column(name = "description", columnDefinition = "MEDIUMTEXT")
  private String description;
  @Column(name = "comment", columnDefinition = "MEDIUMTEXT")
  private String comment;
  @Column(name = "start_time")
  private long startTime;
  @Column(name = "end_time")
  private long endTime;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;
  @Column(name = "draft_status", columnDefinition = "int default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过
  @Column(name = "author", columnDefinition = "varchar(255) default '13700000001'")
  private String author;

  public GroupShopping() {
  }

  public GroupShopping(String title, long price, String coverUrl,
      List<String> tags, String contentUrl, String information, String description,
      String comment, long startTime, long endTime, long createTime, long updateTime,
      boolean isActive, int draftStatus, String author) {
    this.title = title;
    this.price = price;
    this.coverUrl = coverUrl;
    this.tags = tags;
    this.contentUrl = contentUrl;
    this.information = information;
    this.description = description;
    this.comment = comment;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.draftStatus = draftStatus;
    this.author = author;
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

  public String getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(String contentUrl) {
    this.contentUrl = contentUrl;
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
