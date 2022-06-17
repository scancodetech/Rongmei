package com.rongmei.entity.account;



public class Items {

    private int id;
    //二级菜单需要关联的一级父类菜单id
    private int parentId;
    private String name;
    //菜单级别
    private Long itype;
    //菜单具备的权限名称
    private String authority;

    public Items() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getItype() {
        return itype;
    }

    public void setItype(Long itype) {
        this.itype = itype;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
