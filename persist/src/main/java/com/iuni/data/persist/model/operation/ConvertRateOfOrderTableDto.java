package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * 订单转化率结果
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class ConvertRateOfOrderTableDto extends AbstractTableDto {
    /**
     * 日期
     */
    private String date;
    /**
     * 商城PV
     */
    private long pv;
    /**
     * 商城UV
     */
    private long uv;
    /**
     * 订单总数(含无效订单)
     */
    private long orderNum;
    /**
     * 订单转化率
     */
//    private double convertRate;
    /**
     * 已支付订单数
     */
    private long paidOrderNum;
    /**
     * 已支付订单比例
     */
//    private double paidOrderRate;
    /**
     * 在线支付订单数
     */
    private long onlinePaidOrderNum;
    /**
     * 在线支付订单比例
     */
//    private double onlinePaidOrderRate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

//    public double getConvertRate() {
//        return convertRate;
//    }
//
//    public void setConvertRate(double convertRate) {
//        this.convertRate = convertRate;
//    }

    public long getPaidOrderNum() {
        return paidOrderNum;
    }

    public void setPaidOrderNum(long paidOrderNum) {
        this.paidOrderNum = paidOrderNum;
    }

//    public double getPaidOrderRate() {
//        return paidOrderRate;
//    }
//
//    public void setPaidOrderRate(double paidOrderRate) {
//        this.paidOrderRate = paidOrderRate;
//    }

    public long getOnlinePaidOrderNum() {
        return onlinePaidOrderNum;
    }

    public void setOnlinePaidOrderNum(long onlinePaidOrderNum) {
        this.onlinePaidOrderNum = onlinePaidOrderNum;
    }

//    public double getOnlinePaidOrderRate() {
//        return onlinePaidOrderRate;
//    }
//
//    public void setOnlinePaidOrderRate(double onlinePaidOrderRate) {
//        this.onlinePaidOrderRate = onlinePaidOrderRate;
//    }

    /**
     * 生成表头
     *
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("商城PV", "pv");
        tableHeader.put("商城UV", "uv");
        tableHeader.put("订单总数(含无效订单)", "orderNum");
        tableHeader.put("订单转化率", "convertRate");
        tableHeader.put("已支付订单数", "paidOrderNum");
        tableHeader.put("已支付订单比例", "paidOrderRate");
        tableHeader.put("在线支付订单数", "onlinePaidOrderNum");
        tableHeader.put("在线支付订单比例", "onlinePaidOrderRate");
        return tableHeader;
    }

    /**
     * 生成表数据
     *
     * @param convertRateOfOrderTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<ConvertRateOfOrderTableDto> convertRateOfOrderTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (ConvertRateOfOrderTableDto convertRateOfOrderTableDto : convertRateOfOrderTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date", convertRateOfOrderTableDto.getDate());
            rowData.put("pv", convertRateOfOrderTableDto.getPv());
            rowData.put("uv", convertRateOfOrderTableDto.getUv());
            rowData.put("orderNum", convertRateOfOrderTableDto.getOrderNum());
            rowData.put("convertRate", convertRateOfOrderTableDto.getOrderNum() / convertRateOfOrderTableDto.getUv());
            rowData.put("paidOrderNum", convertRateOfOrderTableDto.getPaidOrderNum());
            rowData.put("paidOrderRate", convertRateOfOrderTableDto.getPaidOrderNum() / convertRateOfOrderTableDto.getOrderNum());
            rowData.put("onlinePaidOrderNum", convertRateOfOrderTableDto.getOnlinePaidOrderNum());
            rowData.put("onlinePaidOrderRate", convertRateOfOrderTableDto.getOnlinePaidOrderNum() / convertRateOfOrderTableDto.getPaidOrderNum());
            tableData.add(rowData);
        }
        return tableData;
    }

}

