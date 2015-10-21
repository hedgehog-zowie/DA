package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class RebatesDetailsTableDto extends AbstractTableDto {

    private String orderSource;
    private Date stockChangeTime;
    private String orderCode;
    private String outerOrderCode;
    private String deliveryCode;
    private String skuCode;
    private String materialCode;
    private String goodsName;
    private String skuName;
    private String quantity;
    private String invoiceTCode;
    private String invoiceCode;
    private String invoiceAmount;
    private String logisticsCost;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public Date getStockChangeTime() {
        return stockChangeTime;
    }

    public void setStockChangeTime(Date stockChangeTime) {
        this.stockChangeTime = stockChangeTime;
    }

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

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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

    public String getInvoiceTCode() {
        return invoiceTCode;
    }

    public void setInvoiceTCode(String invoiceTCode) {
        this.invoiceTCode = invoiceTCode;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(String logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("销售渠道/类型", "orderSource");
        tableHeader.put("日期", "stockChangeTime");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("出库单号", "deliveryCode");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("商品名称", "goodsName");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("发票代码", "invoiceTCode");
        tableHeader.put("发票号码", "invoiceCode");
        tableHeader.put("发票金额", "invoiceAmount");
        tableHeader.put("价外费", "logisticsCost");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<RebatesDetailsTableDto> rebatesDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(RebatesDetailsTableDto rebatesDetailsTableDto: rebatesDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSource", rebatesDetailsTableDto.getOrderSource());
            rowData.put("stockChangeTime", rebatesDetailsTableDto.getStockChangeTime());
            rowData.put("orderCode", rebatesDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", rebatesDetailsTableDto.getOuterOrderCode());
            rowData.put("deliveryCode", rebatesDetailsTableDto.getDeliveryCode());
            rowData.put("skuCode", rebatesDetailsTableDto.getSkuCode());
            rowData.put("materialCode", rebatesDetailsTableDto.getMaterialCode());
            rowData.put("goodsName", rebatesDetailsTableDto.getGoodsName());
            rowData.put("skuName", rebatesDetailsTableDto.getSkuName());
            rowData.put("quantity", rebatesDetailsTableDto.getQuantity());
            rowData.put("invoiceTCode", rebatesDetailsTableDto.getInvoiceTCode());
            rowData.put("invoiceCode", rebatesDetailsTableDto.getInvoiceCode());
            rowData.put("invoiceAmount", rebatesDetailsTableDto.getInvoiceAmount());
            rowData.put("logisticsCost", rebatesDetailsTableDto.getLogisticsCost());
            tableData.add(rowData);
        }
        return tableData;
    }
}
