package com.rongmei.entity.auth;

import javax.persistence.*;

@Entity
@Table(name = "captcha")
public class Captcha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "phone")
    private String phone;//用户名
    @Column(name = "code")
    private String code;
    @Column(name = "createTime")
    private long createTime;

    public Captcha() {
    }

    public Captcha(String phone, String code, long createTime) {
        this.phone = phone;
        this.code = code;
        this.createTime = createTime;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
