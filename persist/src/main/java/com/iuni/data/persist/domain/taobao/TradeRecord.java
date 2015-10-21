package com.iuni.data.persist.domain.taobao;

import javax.persistence.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_ALIPAY_TRADE_RECORD")
public class TradeRecord {

    private static final long serialVersionUID = 1829744119271298696L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    /**
     * 支付宝订单号
     */
    @Column(name = "ALIPAY_ORDER_NO")
    private String alipayOrderNo;

    /**
     * 订单创建时间
     */
    @Column(name = "CREATE_TIME")
    private String createTime;

    /**
     * 资金流入流程类型,in表示收入,out表示支出
     */
    @Column(name = "IN_OUT_TYPE")
    private String inOutType;

    /**
     * 商户订单号
     */
    @Column(name = "MERCHANT_ORDER_NO")
    private String merchantOrderNo;

    /**
     * 订单最后修改时间
     */
    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    /**
     * 对方支付宝登录号，需要隐藏
     */
    @Column(name = "OPPOSITE_LOGON_ID")
    private String oppositeLogonId;

    /**
     * 对方姓名，需要隐藏
     */
    @Column(name = "OPPOSITE_NAME")
    private String oppositeName;

    /**
     * 对方支付宝账号
     */
    @Column(name = "OPPOSITE_USER_ID")
    private String oppositeUserId;

    /**
     * 订单来源，为空查询所有来源。淘宝(taobao)，支付宝(alipay)，其它(other)
     */
    @Column(name = "ORDER_FROM")
    private String orderFrom;

    /**
     * 订单状态
     */
    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    /**
     * 订单的名称，描述订单的摘要信息，如交易的商品名称
     */
    @Column(name = "ORDER_TITLE")
    private String orderTitle;

    /**
     * 订单类型
     */
    @Column(name = "ORDER_TYPE")
    private String orderType;

    /**
     * 本方支付宝登录号，需要隐藏
     */
    @Column(name = "OWNER_LOGON_ID")
    private String ownerLogonId;

    /**
     * 本方姓名，需要隐藏
     */
    @Column(name = "OWNER_NAME")
    private String ownerName;

    /**
     * 本方支付宝账号
     */
    @Column(name = "OWNER_USER_ID")
    private String ownerUserId;

    /**
     * 订单服务费
     */
    @Column(name = "SERVICE_CHARGE")
    private String serviceCharge;

    /**
     * 订单总金额
     */
    @Column(name = "TOTAL_AMOUNT")
    private String totalAmount;

    public String getAlipayOrderNo() {
        return this.alipayOrderNo;
    }

    public void setAlipayOrderNo(String alipayOrderNo) {
        this.alipayOrderNo = alipayOrderNo;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInOutType() {
        return this.inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    public String getMerchantOrderNo() {
        return this.merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getOppositeLogonId() {
        return this.oppositeLogonId;
    }

    public void setOppositeLogonId(String oppositeLogonId) {
        this.oppositeLogonId = oppositeLogonId;
    }

    public String getOppositeName() {
        return this.oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getOppositeUserId() {
        return this.oppositeUserId;
    }

    public void setOppositeUserId(String oppositeUserId) {
        this.oppositeUserId = oppositeUserId;
    }

    public String getOrderFrom() {
        return this.orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTitle() {
        return this.orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOwnerLogonId() {
        return this.ownerLogonId;
    }

    public void setOwnerLogonId(String ownerLogonId) {
        this.ownerLogonId = ownerLogonId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerUserId() {
        return this.ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getServiceCharge() {
        return this.serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TradeRecord{" +
                "alipayOrderNo='" + alipayOrderNo + '\'' +
                ", createTime=" + createTime +
                ", inOutType='" + inOutType + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", modifiedTime=" + modifiedTime +
                ", oppositeLogonId='" + oppositeLogonId + '\'' +
                ", oppositeName='" + oppositeName + '\'' +
                ", oppositeUserId='" + oppositeUserId + '\'' +
                ", orderFrom='" + orderFrom + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", ownerLogonId='" + ownerLogonId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerUserId='" + ownerUserId + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
