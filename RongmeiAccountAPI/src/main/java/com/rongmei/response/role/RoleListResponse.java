package com.rongmei.response.role;

import com.rongmei.entity.account.RoleE;
import com.rongmei.response.Response;

import java.util.List;

public class RoleListResponse extends Response {
    private List<RoleE> roleEList;
    public RoleListResponse() {
    }

    public RoleListResponse(List<RoleE> roleEList) {
        this.roleEList = roleEList;
    }

    public List<RoleE> getRoleEList() {
        return roleEList;
    }

    public void setRoleEList(List<RoleE> roleEList) {
        this.roleEList = roleEList;
    }
}
