package com.rongmei.parameters.user;



public class AddRoleParameters {

  private String roleName;//角色名称

  private String roleStr;//角色描述

  private String[] itemNames;//角色具有的权限数组
  public AddRoleParameters() {
  }

  public AddRoleParameters(String roleName, String roleStr, String[] itemNames) {
    this.roleName = roleName;
    this.roleStr = roleStr;
    this.itemNames = itemNames;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleStr() {
    return roleStr;
  }

  public void setRoleStr(String roleStr) {
    this.roleStr = roleStr;
  }

  public String[]  getItemNames() {
    return itemNames;
  }

  public void setItemNames(String[] itemNames) {
    this.itemNames = itemNames;
  }
}
