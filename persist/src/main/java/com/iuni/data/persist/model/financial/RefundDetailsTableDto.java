package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class RefundDetailsTableDto extends AbstractTableDto {

    private String orderCode;
    private String outerOrderCode;
    private Date addTime;
    private Date logTime;
    private String skuCode;
    private String materialCode;
    private String goodsName;
    private String goodsNum;
    private String goodsAttr;
    private String goodsPrice;
    private String goodsAmount;
    private String orderStatus;
    private String payName;
    private String bonus;
    private String orderSource;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("退款业务创建时间", "addTime");
        tableHeader.put("退款时间", "logTime");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("商品名称", "goodsName");
        tableHeader.put("数量", "goodsNum");
        tableHeader.put("属性", "goodsAttr");
        tableHeader.put("商品价格", "goodsPrice");
        tableHeader.put("商品金额", "goodsAmount");
        tableHeader.put("订单状态", "orderStatus");
        tableHeader.put("支付方式", "payName");
        tableHeader.put("优惠券", "bonus");
        tableHeader.put("订单来源", "orderSource");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<RefundDetailsTableDto> refundDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (RefundDetailsTableDto refundDetailsTableDto : refundDetailsTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderCode", refundDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", refundDetailsTableDto.getOuterOrderCode());
            rowData.put("addTime", refundDetailsTableDto.getAddTime());
            rowData.put("logTime", refundDetailsTableDto.getLogTime());
            rowData.put("skuCode", refundDetailsTableDto.getSkuCode());
            rowData.put("materialCode", refundDetailsTableDto.getMaterialCode());
            rowData.put("goodsName", refundDetailsTableDto.getGoodsName());
            rowData.put("goodsNum", refundDetailsTableDto.getGoodsNum());
            rowData.put("goodsAttr", refundDetailsTableDto.getGoodsAttr());
            rowData.put("goodsPrice", refundDetailsTableDto.getGoodsPrice());
            rowData.put("goodsAmount", refundDetailsTableDto.getGoodsAmount());
            rowData.put("orderStatus", refundDetailsTableDto.getOrderStatus());
            rowData.put("payName", refundDetailsTableDto.getPayName());
            rowData.put("bonus", refundDetailsTableDto.getBonus());
            rowData.put("orderSource", refundDetailsTableDto.getOrderSource());
            tableData.add(rowData);
        }
        return tableData;
    }
}
