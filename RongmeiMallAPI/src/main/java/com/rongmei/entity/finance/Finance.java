package com.rongmei.entity.finance;

import com.rongmei.util.BalanceUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "finance")
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "username")
    private String username;

    @Column(name = "create_time" )
    private long createTime;

    //类型 活动 盒蛋 素材 竞品 提现 充值
    @Column(name = "type")
    @NotBlank
    private String type;

    //交易类型
    @Column(name = "transaction_type")
    @NotBlank
    private String transactionType;

    //交易流水单号
    @Column(name = "order_number")
    @NotBlank
    private String orderNumber;

    //起拍价
    @Column(name = "start_price", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal startPrice;


    @Column(name = "transaction_cash" , columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal transactionCash;

    @Column(name = "service_cash" , columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal serviceCash;

    //收入
    @Column(name = "income_cash" , columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal incomeCash;

    //支出
    @Column(name = "expenditure_cash" , columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal expenditureCash;



    //交易额分成
    @Column(name = "transaction_share", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal transactionShare;



    //平台分成 gugu
    @Column(name = "gugu_share" , columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal guguShare;



    //版权
    @Column(name = "copyright_cash", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal copyrightCash;

    //保证金
    @Column(name = "margin", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal margin;

    //取消
    @Column(name = "cancelCash", columnDefinition = "decimal(19) not null DEFAULT 0")
    private BigDecimal cancelCash;

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    //描述
    @Column(name = "descs")
    private String descs;

    //描述
    @Column(name = "pay_type")
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getIsMargin() {
        return isMargin;
    }

    public void setIsMargin(int isMargin) {
        this.isMargin = isMargin;
    }

    //是否保证金
    @Column(name = "is_margin", columnDefinition = " tinyint DEFAULT 1")
    private int isMargin;

    public BigDecimal getCancelCash() {
        return cancelCash;
    }

    public void setCancelCash(BigDecimal cancelCash) {
        this.cancelCash = cancelCash;
    }

    public Finance() {
    }

    public Finance(long id,
                   String username,
                   long createTime,
                   String type,
                   String transactionType,
                   BigDecimal startPrice,
                   BigDecimal transactionCash,
                   BigDecimal margin,
                   BigDecimal copyrightCash,
                   BigDecimal guguShare,
                   BigDecimal transactionShare,
                   BigDecimal expenditureCash,
                   BigDecimal incomeCash,
                   BigDecimal serviceCash,
                   BigDecimal cancelCash,
                   Integer isMargin,
                   String orderNumber,
                   String descs,
                   String payType
    ){
        this.id=id;
        this.username = username;
        this.createTime = createTime;
        this.type = type;
        this.transactionType = transactionType;
        this.startPrice = BalanceUtil.KeepTwoDecimalPlaces(startPrice);
        this.transactionCash = BalanceUtil.KeepTwoDecimalPlaces(transactionCash);
        this.margin = BalanceUtil.KeepTwoDecimalPlaces(margin);
        this.copyrightCash = BalanceUtil.KeepTwoDecimalPlaces(copyrightCash);
        this.guguShare = BalanceUtil.KeepTwoDecimalPlaces(guguShare);
        this.transactionShare = BalanceUtil.KeepTwoDecimalPlaces(transactionShare);
        this.expenditureCash = BalanceUtil.KeepTwoDecimalPlaces(expenditureCash);
        this.incomeCash = BalanceUtil.KeepTwoDecimalPlaces(incomeCash);
        this.serviceCash = BalanceUtil.KeepTwoDecimalPlaces(serviceCash);
        this.cancelCash = BalanceUtil.KeepTwoDecimalPlaces(cancelCash);
        this.isMargin = isMargin;
        this.orderNumber = orderNumber;
        this.descs = descs;
        this.payType = payType;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice =BalanceUtil.KeepTwoDecimalPlaces(startPrice);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionCash() {
        return transactionCash;
    }

    public void setTransactionCash(BigDecimal transactionCash) {
        this.transactionCash =  BalanceUtil.KeepTwoDecimalPlaces(transactionCash);
    }

    public BigDecimal getServiceCash() {
        return serviceCash;
    }

    public void setServiceCash(BigDecimal serviceCash) {
        this.serviceCash =  BalanceUtil.KeepTwoDecimalPlaces(serviceCash);
    }

    public BigDecimal getIncomeCash() {
        return incomeCash;
    }

    public void setIncomeCash(BigDecimal incomeCash) {
        this.incomeCash =  BalanceUtil.KeepTwoDecimalPlaces(incomeCash);
    }

    public BigDecimal getExpenditureCash() {
        return expenditureCash;
    }

    public void setExpenditureCash(BigDecimal expenditureCash) {
        this.expenditureCash =  BalanceUtil.KeepTwoDecimalPlaces(expenditureCash);
    }

    public BigDecimal getTransactionShare() {
        return transactionShare;
    }

    public void setTransactionShare(BigDecimal transactionShare) {
        this.transactionShare =  BalanceUtil.KeepTwoDecimalPlaces(transactionShare);
    }

    public BigDecimal getGuguShare() {
        return guguShare;
    }

    public void setGuguShare(BigDecimal guguShare) {
        this.guguShare =  BalanceUtil.KeepTwoDecimalPlaces(guguShare);
    }

    public BigDecimal getCopyrightCash() {
        return copyrightCash;
    }

    public void setCopyrightCash(BigDecimal copyrightCash) {
        this.copyrightCash =  BalanceUtil.KeepTwoDecimalPlaces(copyrightCash);
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setMargin(BigDecimal margin) {
        this.margin =  BalanceUtil.KeepTwoDecimalPlaces(margin);
    }

    @Override
    public String toString() {
        return "Finance{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", createTime=" + createTime +
                ", type='" + type + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", startPrice=" + startPrice +
                ", transactionCash=" + transactionCash +
                ", serviceCash=" + serviceCash +
                ", incomeCash=" + incomeCash +
                ", expenditureCash=" + expenditureCash +
                ", transactionShare=" + transactionShare +
                ", guguShare=" + guguShare +
                ", copyrightCash=" + copyrightCash +
                ", orderNumber=" + orderNumber +
                ", margin=" + margin +
                ", cancelCash=" + cancelCash +
                '}';
    }
}
