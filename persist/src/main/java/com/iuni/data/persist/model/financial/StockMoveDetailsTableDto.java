package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockMoveDetailsTableDto extends AbstractTableDto {

    private String orderSource;
    private String payName;
    private String warehouseName;
    private Date stockChangeTime;
    private String orderCode;
    private String outerOrderCode;
    private String skuCode;
    private String materialCode;
    private String skuName;
    private String quantity;
    private String invoiceTCode;
    private String invoiceCode;
    private String invoiceAmount;
    private String logisticsCost;
    private String isScalper;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getIsScalper() {
        return isScalper;
    }

    public void setIsScalper(String isScalper) {
        this.isScalper = isScalper;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("销售渠道/类型", "orderSource");
        tableHeader.put("收款类型", "payName");
        tableHeader.put("仓库", "warehouseName");
        tableHeader.put("日期", "stockChangeTime");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("发票代码", "invoiceTCode");
        tableHeader.put("发票号码", "invoiceCode");
        tableHeader.put("发票金额", "invoiceAmount");
        tableHeader.put("价外费", "logisticsCost");
        tableHeader.put("是否刷单", "isScalper");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockMoveDetailsTableDto> stockMoveDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (StockMoveDetailsTableDto stockMoveDetailsTableDto : stockMoveDetailsTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSource", stockMoveDetailsTableDto.getOrderSource());
            rowData.put("payName", stockMoveDetailsTableDto.getPayName());
            rowData.put("warehouseName", stockMoveDetailsTableDto.getWarehouseName());
            rowData.put("stockChangeTime", stockMoveDetailsTableDto.getStockChangeTime());
            rowData.put("orderCode", stockMoveDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", stockMoveDetailsTableDto.getOuterOrderCode());
            rowData.put("skuCode", stockMoveDetailsTableDto.getSkuCode());
            rowData.put("materialCode", stockMoveDetailsTableDto.getMaterialCode());
            rowData.put("skuName", stockMoveDetailsTableDto.getSkuName());
            rowData.put("quantity", stockMoveDetailsTableDto.getQuantity());
            rowData.put("invoiceTCode", stockMoveDetailsTableDto.getInvoiceTCode());
            rowData.put("invoiceCode", stockMoveDetailsTableDto.getInvoiceCode());
            rowData.put("invoiceAmount", stockMoveDetailsTableDto.getInvoiceAmount());
            rowData.put("logisticsCost", stockMoveDetailsTableDto.getLogisticsCost());
            rowData.put("isScalper", stockMoveDetailsTableDto.getIsScalper());
            tableData.add(rowData);
        }
        return tableData;
    }

}
