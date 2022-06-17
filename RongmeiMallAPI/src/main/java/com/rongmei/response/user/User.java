package com.rongmei.response.user;

import com.rongmei.publicdatas.account.Role;

public class User {

  private int id;
  private String phone;
  private Role role;

  public User() {
  }

  public User(int id, String phone, Role role) {
    this.id = id;
    this.phone = phone;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
