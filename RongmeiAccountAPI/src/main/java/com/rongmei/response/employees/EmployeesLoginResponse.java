package com.rongmei.response.employees;

import com.rongmei.entity.account.RoleE;
import com.rongmei.response.Response;

public class EmployeesLoginResponse extends Response {
    private String token;
    private RoleE role;

    public EmployeesLoginResponse() {
    }

    public EmployeesLoginResponse(String token, RoleE role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoleE getRole() {
        return role;
    }

    public void setRole(RoleE role) {
        this.role = role;
    }
}
