package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReverseSignOfBackTableDto extends AbstractTableDto{

    private String orderSn;
    private String deliverySn;
    private String status;
    private String isInvoice;
    private String goodsName;
    private String userName;
    private String mobile;
    private String address;
    private Date processTime;
    private Date auditTime;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
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

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单号", "orderSn");
        tableHeader.put("退货单号", "deliverySn");
        tableHeader.put("退货状态", "status");
        tableHeader.put("是否退回发票", "isInvoice");
        tableHeader.put("退回实物", "goodsName");
        tableHeader.put("客户姓名", "userName");
        tableHeader.put("联系电话", "mobile");
        tableHeader.put("联系地址", "address");
        tableHeader.put("签收时间", "processTime");
        tableHeader.put("客服审核时间", "auditTime");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<ReverseSignOfBackTableDto> reverseSignOfBackTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(ReverseSignOfBackTableDto reverseSignOfBackTableDto: reverseSignOfBackTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSn", reverseSignOfBackTableDto.getOrderSn());
            rowData.put("deliverySn", reverseSignOfBackTableDto.getDeliverySn());
            rowData.put("status", reverseSignOfBackTableDto.getStatus());
            rowData.put("isInvoice", reverseSignOfBackTableDto.getIsInvoice());
            rowData.put("goodsName", reverseSignOfBackTableDto.getGoodsName());
            rowData.put("userName", reverseSignOfBackTableDto.getUserName());
            rowData.put("mobile", reverseSignOfBackTableDto.getMobile());
            rowData.put("address", reverseSignOfBackTableDto.getAddress());
            rowData.put("processTime", reverseSignOfBackTableDto.getProcessTime());
            rowData.put("auditTime", reverseSignOfBackTableDto.getAuditTime());
            tableData.add(rowData);
        }
        return tableData;
    }

}
