package com.iuni.data.persist.model.activity;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ActivityChannelTableDto extends AbstractTableDto{

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

    /**
     * 表头
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "time");
        tableHeader.put("推广渠道", "channelName");
        tableHeader.put("推广链接", "channelUrl");
        tableHeader.put("PV", "pv");
        tableHeader.put("UV", "uv");
        tableHeader.put("VV", "vv");
        tableHeader.put("跳出率", "jumpRate");
        tableHeader.put("人均浏览页面", "avrPages");
        tableHeader.put("平均访问深度", "avrDeeps");
        tableHeader.put("平均访问时间", "avrTimes");
        tableHeader.put("注册页UV", "ruv");
        tableHeader.put("注册成功数", "rsNum");
        tableHeader.put("注册转化率", "rRate");
        tableHeader.put("注册成功率", "rsRate");
        tableHeader.put("下单总数量", "orderNum");
        tableHeader.put("下单总金额", "orderAmount");
        tableHeader.put("下单转化率", "orderTrans");
        tableHeader.put("已支付订单数", "paidOrderNum");
        tableHeader.put("已支付订单比", "payRate");
        tableHeader.put("已支付订单金额", "paidOrderAmount");
        tableHeader.put("客单价", "avgAmount");
        return tableHeader;
    }

    /**
     * 表数据
     * @param channelTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<ActivityChannelTableDto> channelTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (ActivityChannelTableDto channelTableDto : channelTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("time", channelTableDto.getTime());
            rowData.put("channelName", channelTableDto.getChannelName());
            rowData.put("channelUrl", channelTableDto.getChannelUrl());
            rowData.put("pv", channelTableDto.getPv());
            rowData.put("uv", channelTableDto.getUv());
            rowData.put("vv", channelTableDto.getVv());
            rowData.put("jumpRate", channelTableDto.getJumpRate());
            rowData.put("avrPages", channelTableDto.getAvrPages());
            rowData.put("avrDeeps", channelTableDto.getAvrDeeps());
            rowData.put("avrTimes", channelTableDto.getAvrTimes());
            rowData.put("ruv", channelTableDto.getRuv());
            rowData.put("rsNum", channelTableDto.getRsNum());
            rowData.put("rRate", channelTableDto.getrRate());
            rowData.put("rsRate", channelTableDto.getRsRate());
            rowData.put("orderNum", channelTableDto.getOrderNum());
            rowData.put("orderAmount", channelTableDto.getOrderAmount());
            rowData.put("orderTrans", channelTableDto.getOrderTrans());
            rowData.put("paidOrderNum", channelTableDto.getPaidOrderAmount());
            rowData.put("payRate", channelTableDto.getPayRate());
            rowData.put("paidOrderAmount", channelTableDto.getPaidOrderAmount());
            rowData.put("avgAmount", channelTableDto.getAvgAmount());
            tableData.add(rowData);
        }
        return tableData;
    }
}
