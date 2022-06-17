package com.rongmei.parameters.user;

public class UserRoleUpdateParameters {

  private String role;
  private int id;

  public UserRoleUpdateParameters() {
  }

  public UserRoleUpdateParameters(String role, int id) {
    this.role = role;
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
