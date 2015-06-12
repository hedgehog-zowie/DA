package com.iuni.data.persist.domain.taobao;

import javax.persistence.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_ALIPAY_RECORD")
public class AlipayRecord {

    private static final long serialVersionUID = 2576416512633551235L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    /**
     * 支付宝订单号
     */
    @Column(name = "ALIPAY_ORDER_NO")
    private String alipayOrderNo;

    /**
     * 当时支付宝账户余额
     */
    @Column(name = "BALANCE")
    private String balance;

    /**
     * 子业务类型
     */
    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    /**
     * 收入金额
     */
    @Column(name = "IN_AMOUNT")
    private String inAmount;

    /**
     * 账号备注
     */
    @Column(name = "MEMO")
    private String memo;

    /**
     * 支付宝订单号
     */
    @Column(name = "MERCHANT_ORDER_NO")
    private String merchantOrderNo;

    /**
     * 对方的支付宝ID
     */
    @Column(name = "OPT_USER_ID")
    private String optUserId;

    /**
     * 支出金额
     */
    @Column(name = "OUT_AMOUNT")
    private String outAmount;

    /**
     * 自己的支付宝ID
     */
    @Column(name = "SELF_USER_ID")
    private String selfUserId;

    /**
     * 账务类型
     */
    @Column(name = "TYPE")
    private String type;

    public String getAlipayOrderNo() {
        return this.alipayOrderNo;
    }

    public void setAlipayOrderNo(String alipayOrderNo) {
        this.alipayOrderNo = alipayOrderNo;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInAmount() {
        return this.inAmount;
    }

    public void setInAmount(String inAmount) {
        this.inAmount = inAmount;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMerchantOrderNo() {
        return this.merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getOptUserId() {
        return this.optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getOutAmount() {
        return this.outAmount;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    public String getSelfUserId() {
        return this.selfUserId;
    }

    public void setSelfUserId(String selfUserId) {
        this.selfUserId = selfUserId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AlipayRecord{" +
                "alipayOrderNo='" + alipayOrderNo + '\'' +
                ", balance='" + balance + '\'' +
                ", businessType='" + businessType + '\'' +
                ", createTime='" + createTime + '\'' +
                ", inAmount='" + inAmount + '\'' +
                ", memo='" + memo + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", optUserId='" + optUserId + '\'' +
                ", outAmount='" + outAmount + '\'' +
                ", selfUserId='" + selfUserId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public AlipayRecord copy(com.taobao.api.domain.AlipayRecord alipayRecord) {
        this.alipayOrderNo = alipayRecord.getAlipayOrderNo();
        this.balance = alipayRecord.getBalance();
        this.memo = alipayRecord.getMemo();
        this.alipayOrderNo = alipayRecord.getAlipayOrderNo();
        this.optUserId = alipayRecord.getOptUserId();
        this.merchantOrderNo = alipayRecord.getMerchantOrderNo();
        this.createTime = alipayRecord.getCreateTime();
        this.selfUserId = alipayRecord.getSelfUserId();
        this.businessType = alipayRecord.getBusinessType();
        this.outAmount = alipayRecord.getOutAmount();
        this.type = alipayRecord.getType();
        this.inAmount = alipayRecord.getInAmount();
        return this;
    }

}
