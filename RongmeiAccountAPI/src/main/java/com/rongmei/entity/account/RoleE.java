package com.rongmei.entity.account;


import javax.persistence.*;

//后台管理用户角色
@Entity
@Table(name = "role")
public class RoleE {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    //角色描述
    @Column(name = "role_str")
    private String roleStr;


    public RoleE() {

    }

    public RoleE(int id, String name, String roleStr) {
        this.id = id;
        this.name = name;
        this.roleStr = roleStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleStr() {
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

}
