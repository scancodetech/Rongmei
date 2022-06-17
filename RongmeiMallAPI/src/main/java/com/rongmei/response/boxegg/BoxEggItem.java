package com.rongmei.response.boxegg;

import javax.persistence.Column;

public class BoxEggItem {
    private  int id;

    private String seriesName;//系列名称

    private String specification;//套盒规格  1: 3*3 ， 2: 3*4；

    private String  coverUrl;//封面图路径

    private String  boxEggName;//盒蛋名称

    private String boxEggIntroduction;//盒蛋简介

    private int limitNumber;//限量数

    private int publishNumber;//已经发布的数量

    private int price;//盒蛋价格

    private String walletAddress;//钱包地址

    private String resourceInfo;//资源信息

    private long createTime;//创建时间

    private long updateTime;//更新时间

    public BoxEggItem() {
    }

    public BoxEggItem(int id, String seriesName, String specification, String coverUrl, String boxEggName, String boxEggIntroduction, int limitNumber, int publishNumber, int price, String walletAddress, String resourceInfo, long createTime, long updateTime) {
        this.id = id;
        this.seriesName = seriesName;
        this.specification = specification;
        this.coverUrl = coverUrl;
        this.boxEggName = boxEggName;
        this.boxEggIntroduction = boxEggIntroduction;
        this.limitNumber = limitNumber;
        this.publishNumber = publishNumber;
        this.price = price;
        this.walletAddress = walletAddress;
        this.resourceInfo = resourceInfo;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getPublishNumber() {
        return publishNumber;
    }

    public void setPublishNumber(int publishNumber) {
        this.publishNumber = publishNumber;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBoxEggName() {
        return boxEggName;
    }

    public void setBoxEggName(String boxEggName) {
        this.boxEggName = boxEggName;
    }

    public String getBoxEggIntroduction() {
        return boxEggIntroduction;
    }

    public void setBoxEggIntroduction(String boxEggIntroduction) {
        this.boxEggIntroduction = boxEggIntroduction;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(String resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
