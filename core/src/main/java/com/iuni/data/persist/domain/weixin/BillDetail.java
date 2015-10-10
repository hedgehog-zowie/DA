package com.iuni.data.persist.domain.weixin;

import javax.persistence.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_WEIXIN_PAY_BILL_DETAIL")
public class BillDetail {

    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @Column(name = "TRADE_DATE")
    private String tradeDate;
    @Column(name = "PUBLIC_ACCOUNT")
    private String publicAccount;
    @Column(name = "MCH_ID")
    private String mchId;
    @Column(name = "SUB_MCH_ID")
    private String subMchId;
    @Column(name = "DEVICE")
    private String device;
    @Column(name = "WX_ORDER_NO")
    private String wxOrderSn;
    @Column(name = "MCH_ORDER_SN")
    private String mchOrderSn;
    @Column(name = "USER_SIGN")
    private String userSign;
    @Column(name = "TRADE_TYPE")
    private String tradeType;
    @Column(name = "TRADE_STATUS")
    private String tradeStatus;
    @Column(name = "BANK")
    private String bank;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "DISCOUNT")
    private String discount;
    @Column(name = "WX_REFUND_SN")
    private String wxRefundSn;
    @Column(name = "MCH_REFUND_SN")
    private String mchRefundSn;
    @Column(name = "REFUND")
    private String refund;
    @Column(name = "REFUND_DISCOUNT")
    private String refundDiscount;
    @Column(name = "REFUND_TYPE")
    private String refundType;
    @Column(name = "REFUND_STATUS")
    private String refundStatus;
    @Column(name = "MERCHANDISE")
    private String merchandise;
    @Column(name = "MCH_DATA_PAK")
    private String mchDatePak;
    @Column(name = "FEE")
    private String fee;
    @Column(name = "RATE")
    private String rate;

    public BillDetail(String tradeDate, String publicAccount, String mchId, String subMchId, String device, String wxOrderSn, String mchOrderSn, String userSign, String tradeType, String tradeStatus, String bank, String currency, String amount, String discount, String wxRefundSn, String mchRefundSn, String refund, String refundDiscount, String refundType, String refundStatus, String merchandise, String mchDatePak, String fee, String rate) {
        this.tradeDate = tradeDate;
        this.publicAccount = publicAccount;
        this.mchId = mchId;
        this.subMchId = subMchId;
        this.device = device;
        this.wxOrderSn = wxOrderSn;
        this.mchOrderSn = mchOrderSn;
        this.userSign = userSign;
        this.tradeType = tradeType;
        this.tradeStatus = tradeStatus;
        this.bank = bank;
        this.currency = currency;
        this.amount = amount;
        this.discount = discount;
        this.wxRefundSn = wxRefundSn;
        this.mchRefundSn = mchRefundSn;
        this.refund = refund;
        this.refundDiscount = refundDiscount;
        this.refundType = refundType;
        this.refundStatus = refundStatus;
        this.merchandise = merchandise;
        this.mchDatePak = mchDatePak;
        this.fee = fee;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "tradeDate='" + tradeDate + '\'' +
                ", publicAccount='" + publicAccount + '\'' +
                ", mchId='" + mchId + '\'' +
                ", subMchId='" + subMchId + '\'' +
                ", device='" + device + '\'' +
                ", wxOrderSn='" + wxOrderSn + '\'' +
                ", mchOrderSn='" + mchOrderSn + '\'' +
                ", userSign='" + userSign + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", bank='" + bank + '\'' +
                ", currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                ", discount='" + discount + '\'' +
                ", wxRefundSn='" + wxRefundSn + '\'' +
                ", mchRefundSn='" + mchRefundSn + '\'' +
                ", refund='" + refund + '\'' +
                ", refundDiscount='" + refundDiscount + '\'' +
                ", refundType='" + refundType + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", merchandise='" + merchandise + '\'' +
                ", mchDatePak='" + mchDatePak + '\'' +
                ", fee='" + fee + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(String publicAccount) {
        this.publicAccount = publicAccount;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getWxOrderSn() {
        return wxOrderSn;
    }

    public void setWxOrderSn(String wxOrderSn) {
        this.wxOrderSn = wxOrderSn;
    }

    public String getMchOrderSn() {
        return mchOrderSn;
    }

    public void setMchOrderSn(String mchOrderSn) {
        this.mchOrderSn = mchOrderSn;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getWxRefundSn() {
        return wxRefundSn;
    }

    public void setWxRefundSn(String wxRefundSn) {
        this.wxRefundSn = wxRefundSn;
    }

    public String getMchRefundSn() {
        return mchRefundSn;
    }

    public void setMchRefundSn(String mchRefundSn) {
        this.mchRefundSn = mchRefundSn;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getRefundDiscount() {
        return refundDiscount;
    }

    public void setRefundDiscount(String refundDiscount) {
        this.refundDiscount = refundDiscount;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    public String getMchDatePak() {
        return mchDatePak;
    }

    public void setMchDatePak(String mchDatePak) {
        this.mchDatePak = mchDatePak;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


}
