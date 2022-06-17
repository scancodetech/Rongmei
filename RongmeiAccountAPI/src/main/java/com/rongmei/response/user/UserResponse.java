package com.rongmei.response.user;

import com.rongmei.publicdatas.account.Role;
import com.rongmei.response.Response;

public class UserResponse extends Response {

  private int id;
  private String phone;
  private Role role;

  public UserResponse() {
  }

  public UserResponse(int id, String phone, Role role) {
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
