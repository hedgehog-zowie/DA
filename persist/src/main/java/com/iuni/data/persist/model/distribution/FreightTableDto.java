package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class FreightTableDto extends AbstractTableDto{

    private Date shippingTime;
    private String deliveryCode;
    private String orderCode;
    private String status;
    private String userName;
    private String address;
    private String weight;
    private String amount;
    private String protectFee;
    private String deliveryFee;
    private String type;

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProtectFee() {
        return protectFee;
    }

    public void setProtectFee(String protectFee) {
        this.protectFee = protectFee;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("发货时间", "shippingTime");
        tableHeader.put("运单号", "deliveryCode");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("签收情况", "status");
        tableHeader.put("收货人姓名", "userName");
        tableHeader.put("收货人地址", "address");
        tableHeader.put("重量", "weight");
        tableHeader.put("保价金额", "amount");
        tableHeader.put("保价费用", "protectFee");
        tableHeader.put("运输费用", "deliveryFee");
        tableHeader.put("订单类型", "type");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<FreightTableDto> freightTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(FreightTableDto freightTableDto: freightTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("shippingTime", freightTableDto.getShippingTime());
            rowData.put("deliveryCode", freightTableDto.getDeliveryCode());
            rowData.put("orderCode", freightTableDto.getOrderCode());
            rowData.put("status", freightTableDto.getStatus());
            rowData.put("userName", freightTableDto.getUserName());
            rowData.put("address", freightTableDto.getAddress());
            rowData.put("weight", freightTableDto.getWeight());
            rowData.put("amount", freightTableDto.getAmount());
            rowData.put("protectFee", freightTableDto.getProtectFee());
            rowData.put("deliveryFee", freightTableDto.getDeliveryFee());
            rowData.put("type", freightTableDto.getType());
            tableData.add(rowData);
        }
        return tableData;
    }

}
