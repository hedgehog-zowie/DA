package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReturnGoodsTableDto extends AbstractTableDto {

    private Date time;
    private String backChannel;
    private String orderCode;
    private String orderUser;
    private String sku;
    private String skuName;
    private String quantity;
    private String backReason;
    private String remark;
    private String invoice;
    private String shippingName;
    private String backCode;
    private String imei;
    private String handledBy;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBackChannel() {
        return backChannel;
    }

    public void setBackChannel(String backChannel) {
        this.backChannel = backChannel;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getBackCode() {
        return backCode;
    }

    public void setBackCode(String backCode) {
        this.backCode = backCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "time");
        tableHeader.put("退货渠道", "backChannel");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("客户姓名", "orderUser");
        tableHeader.put("SKU", "sku");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("退回原因", "backReason");
        tableHeader.put("备注", "remark");
        tableHeader.put("发票", "invoice");
        tableHeader.put("退回物流", "shippingName");
        tableHeader.put("退回单号", "backCode");
        tableHeader.put("IMEI", "imei");
        tableHeader.put("快递签收人", "handledBy");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<ReturnGoodsTableDto> returnGoodsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(ReturnGoodsTableDto returnGoodsTableDto: returnGoodsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("time", returnGoodsTableDto.getTime());
            rowData.put("backChannel", returnGoodsTableDto.getBackChannel());
            rowData.put("orderCode", returnGoodsTableDto.getOrderCode());
            rowData.put("orderUser", returnGoodsTableDto.getOrderUser());
            rowData.put("sku", returnGoodsTableDto.getSku());
            rowData.put("skuName", returnGoodsTableDto.getSkuName());
            rowData.put("quantity", returnGoodsTableDto.getQuantity());
            rowData.put("backReason", returnGoodsTableDto.getBackReason());
            rowData.put("remark", returnGoodsTableDto.getRemark());
            rowData.put("invoice", returnGoodsTableDto.getInvoice());
            rowData.put("shippingName", returnGoodsTableDto.getShippingName());
            rowData.put("backCode", returnGoodsTableDto.getBackCode());
            rowData.put("imei", returnGoodsTableDto.getImei());
            rowData.put("handledBy", returnGoodsTableDto.getHandledBy());
            tableData.add(rowData);
        }
        return tableData;
    }
}
