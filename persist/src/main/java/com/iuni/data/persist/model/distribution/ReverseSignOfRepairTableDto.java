package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReverseSignOfRepairTableDto extends AbstractTableDto{

    private String orderSn;
    private String repairSn;
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

    public String getRepairSn() {
        return repairSn;
    }

    public void setRepairSn(String repairSn) {
        this.repairSn = repairSn;
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
        tableHeader.put("维修单单号", "repairSn");
        tableHeader.put("维修单状态", "status");
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

    public static List<Map<String, Object>> generateTableData(List<ReverseSignOfRepairTableDto> reverseSignOfRepairTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(ReverseSignOfRepairTableDto reverseSignOfRepairTableDto : reverseSignOfRepairTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSn", reverseSignOfRepairTableDto.getOrderSn());
            rowData.put("repairSn", reverseSignOfRepairTableDto.getRepairSn());
            rowData.put("status", reverseSignOfRepairTableDto.getStatus());
            rowData.put("goodsName", reverseSignOfRepairTableDto.getGoodsName());
            rowData.put("userName", reverseSignOfRepairTableDto.getUserName());
            rowData.put("mobile", reverseSignOfRepairTableDto.getMobile());
            rowData.put("address", reverseSignOfRepairTableDto.getAddress());
            rowData.put("receiveTime", reverseSignOfRepairTableDto.getReceiveTime());
            rowData.put("checkTime", reverseSignOfRepairTableDto.getCheckTime());
            rowData.put("auditTime", reverseSignOfRepairTableDto.getAuditTime());
            rowData.put("shippingTime", reverseSignOfRepairTableDto.getShippingTime());
            tableData.add(rowData);
        }
        return tableData;
    }

}
