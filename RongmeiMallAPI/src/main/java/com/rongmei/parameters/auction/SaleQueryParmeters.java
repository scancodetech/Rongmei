package com.rongmei.parameters.auction;

public class SaleQueryParmeters {

    /**
    收入 0 支出 1
    */
    private Integer type;

    /**
     开始时间
     */
    private Long startTime;


    /**
     结束时间
     */
    private Long endTime;


    /**
    分区
     */
    private Integer partition;

    /**
     0 今天 -1 昨天  1七天前 2一个月前
     */
    private Integer time;




    private Integer limit;


    private Integer offset;

    public SaleQueryParmeters() {
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public SaleQueryParmeters(Integer type, Long startTime, Long endTime, Integer partition, Integer time, Integer limit, Integer offset) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.partition = partition;
        this.time = time;
        this.limit = limit;
        this.offset = offset;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
