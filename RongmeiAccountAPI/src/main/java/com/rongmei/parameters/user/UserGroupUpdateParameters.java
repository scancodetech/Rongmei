package com.rongmei.parameters.user;

import java.util.List;

public class UserGroupUpdateParameters {

  private int id;
  private String title;
  private List<String> usernames;

  public UserGroupUpdateParameters() {
  }

  public UserGroupUpdateParameters(int id, String title, List<String> usernames) {
    this.id = id;
    this.title = title;
    this.usernames = usernames;
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

  public List<String> getUsernames() {
    return usernames;
  }

  public void setUsernames(List<String> usernames) {
    this.usernames = usernames;
  }
}
