package com.rongmei.parameters.user;



public class AddEmployeesParameters {

  private String username;//员工用户名

  private String roleName;//角色名称

  public AddEmployeesParameters() {
  }

  public AddEmployeesParameters(String username, String roleName) {
    this.username = username;
    this.roleName = roleName;
   
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}
