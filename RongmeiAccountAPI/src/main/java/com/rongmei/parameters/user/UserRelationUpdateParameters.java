package com.rongmei.parameters.user;

public class UserRelationUpdateParameters {

  private String fromUsername;
  private String toUsername;
  private int relationType;

  public UserRelationUpdateParameters() {
  }

  public UserRelationUpdateParameters(String fromUsername, String toUsername, int relationType) {
    this.fromUsername = fromUsername;
    this.toUsername = toUsername;
    this.relationType = relationType;
  }

  public String getFromUsername() {
    return fromUsername;
  }

  public void setFromUsername(String fromUsername) {
    this.fromUsername = fromUsername;
  }

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }

  public int getRelationType() {
    return relationType;
  }

  public void setRelationType(int relationType) {
    this.relationType = relationType;
  }
}
