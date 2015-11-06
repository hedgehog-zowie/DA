package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReverseSignOfExchangeTableDto extends AbstractTableDto{

    private String orderSn;
    private String exchangeSn;
    private String status;
    private String goodsName;
    private String userName;
    private String mobile;
    private String address;
    private Date receiveTime;
    private Date checkTime;
    private Date auditTime;
    private Date shippingTime;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getExchangeSn() {
        return exchangeSn;
    }

    public void setExchangeSn(String exchangeSn) {
        this.exchangeSn = exchangeSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单号", "orderSn");
        tableHeader.put("换货单号", "exchangeSn");
        tableHeader.put("换货状态", "status");
        tableHeader.put("退回实物", "goodsName");
        tableHeader.put("客户姓名", "userName");
        tableHeader.put("联系电话", "mobile");
        tableHeader.put("联系地址", "address");
        tableHeader.put("签收时间", "receiveTime");
        tableHeader.put("售后检测时间", "checkTime");
        tableHeader.put("客服审核时间", "auditTime");
        tableHeader.put("发货时间", "shippingTime");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<ReverseSignOfExchangeTableDto> reverseSignOfExchangeTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(ReverseSignOfExchangeTableDto reverseSignOfExchangeTableDto: reverseSignOfExchangeTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSn", reverseSignOfExchangeTableDto.getOrderSn());
            rowData.put("exchangeSn", reverseSignOfExchangeTableDto.getExchangeSn());
            rowData.put("status", reverseSignOfExchangeTableDto.getStatus());
            rowData.put("goodsName", reverseSignOfExchangeTableDto.getGoodsName());
            rowData.put("userName", reverseSignOfExchangeTableDto.getUserName());
            rowData.put("mobile", reverseSignOfExchangeTableDto.getMobile());
            rowData.put("address", reverseSignOfExchangeTableDto.getAddress());
            rowData.put("receiveTime", reverseSignOfExchangeTableDto.getReceiveTime());
            rowData.put("checkTime", reverseSignOfExchangeTableDto.getCheckTime());
            rowData.put("auditTime", reverseSignOfExchangeTableDto.getAuditTime());
            rowData.put("shippingTime", reverseSignOfExchangeTableDto.getShippingTime());
            tableData.add(rowData);
        }
        return tableData;
    }

}
