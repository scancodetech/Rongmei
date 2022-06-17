package com.rongmei.entity.account;


import javax.persistence.*;

@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //二级菜单需要关联的一级父类菜单id
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "name")
    private String name;
    //菜单级别
    @Column(name = "itype")
    private Long itype;
    //菜单对应的权限名称
    @Column(name = "authority")
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
