package com.rongmei.response.user;

import com.rongmei.publicdatas.account.Role;

public class UserItem {

  private int id;
  private String phone;
  private String password;
  private Role role;

  public UserItem() {
  }

  public UserItem(int id, String phone, String password, Role role) {
    this.id = id;
    this.phone = phone;
    this.password = password;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
