package com.rongmei.entity.eventorder;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "event_order")
public class EventOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "type")
    private String type;

    public EventOrder() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EventOrder(
            Long id,
            String username,
            Long createTime,
            BigDecimal price,
            Integer eventId,
            String type
    ){
        this.id = id;
        this.username = username;
        this.createTime = createTime;
        this.price = price;
        this.eventId = eventId;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
