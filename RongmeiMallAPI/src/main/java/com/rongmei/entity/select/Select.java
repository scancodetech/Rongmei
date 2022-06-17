package com.rongmei.entity.select;

import javax.persistence.*;

@Entity
@Table(name = "select_table")
public class Select {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "value")
    private String value;
    @Column(name = "title")
    private String title;
    @Column(name = "sort")
    private Integer sort;

    public Select() {
    }
    public Select(String value, String title, Integer sort) {
        this.value = value;
        this.title = title;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
