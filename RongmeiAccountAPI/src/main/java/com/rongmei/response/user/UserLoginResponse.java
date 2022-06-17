package com.rongmei.response.user;

import com.rongmei.publicdatas.account.Role;
import com.rongmei.response.Response;

public class UserLoginResponse extends Response {

  private String token;
  private Role role;

  public UserLoginResponse() {
  }

  public UserLoginResponse(String token, Role role) {
    this.token = token;
    this.role = role;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
