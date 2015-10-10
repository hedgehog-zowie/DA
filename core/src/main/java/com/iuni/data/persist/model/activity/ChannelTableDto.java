package com.iuni.data.persist.model.activity;

import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ChannelTableDto {

    private Date time;
    private String channelName;
    private String channelCode;
    private String channelUrl;
    private int pv;
    private int uv;
    private int vv;
    private float jumpRate;
    private float avrPages;
    private float avrDeeps;
    private float avrTimes;
    private int ruv;
    private int rsNum;
    private float rRate;
    private float rsRate;
    private int orderNum;
    private int orderAmount;
    private float orderTrans;
    private int paidOrderNum;
    private float payRate;
    private int paidOrderAmount;
    private int avgAmount;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getVv() {
        return vv;
    }

    public void setVv(int vv) {
        this.vv = vv;
    }

    public float getJumpRate() {
        return jumpRate;
    }

    public void setJumpRate(float jumpRate) {
        this.jumpRate = jumpRate;
    }

    public float getAvrPages() {
        return avrPages;
    }

    public void setAvrPages(float avrPages) {
        this.avrPages = avrPages;
    }

    public float getAvrDeeps() {
        return avrDeeps;
    }

    public void setAvrDeeps(float avrDeeps) {
        this.avrDeeps = avrDeeps;
    }

    public float getAvrTimes() {
        return avrTimes;
    }

    public void setAvrTimes(float avrTimes) {
        this.avrTimes = avrTimes;
    }

    public int getRuv() {
        return ruv;
    }

    public void setRuv(int ruv) {
        this.ruv = ruv;
    }

    public int getRsNum() {
        return rsNum;
    }

    public void setRsNum(int rsNum) {
        this.rsNum = rsNum;
    }

    public float getrRate() {
        return rRate;
    }

    public void setrRate(float rRate) {
        this.rRate = rRate;
    }

    public float getRsRate() {
        return rsRate;
    }

    public void setRsRate(float rsRate) {
        this.rsRate = rsRate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public float getOrderTrans() {
        return orderTrans;
    }

    public void setOrderTrans(float orderTrans) {
        this.orderTrans = orderTrans;
    }

    public int getPaidOrderNum() {
        return paidOrderNum;
    }

    public void setPaidOrderNum(int paidOrderNum) {
        this.paidOrderNum = paidOrderNum;
    }

    public float getPayRate() {
        return payRate;
    }

    public void setPayRate(float payRate) {
        this.payRate = payRate;
    }

    public int getPaidOrderAmount() {
        return paidOrderAmount;
    }

    public void setPaidOrderAmount(int paidOrderAmount) {
        this.paidOrderAmount = paidOrderAmount;
    }

    public int getAvgAmount() {
        return avgAmount;
    }

    public void setAvgAmount(int avgAmount) {
        this.avgAmount = avgAmount;
    }
}
