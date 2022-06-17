package com.rongmei.parameters.report;

import java.util.List;

public class ReportSubmitParameters {

  private String reason;
  private String description;
  private List<String> imageUrls;
  private long relationId;

  public ReportSubmitParameters() {
  }

  public ReportSubmitParameters(String reason, String description,
      List<String> imageUrls, long relationId) {
    this.reason = reason;
    this.description = description;
    this.imageUrls = imageUrls;
    this.relationId = relationId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getImageUrls() {
    return imageUrls;
  }

  public void setImageUrls(List<String> imageUrls) {
    this.imageUrls = imageUrls;
  }

  public long getRelationId() {
    return relationId;
  }

  public void setRelationId(long relationId) {
    this.relationId = relationId;
  }
}
