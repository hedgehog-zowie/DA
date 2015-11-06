package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class NoInvoiceSalesDetailsTableDto extends AbstractTableDto {

    private String orderSource;
    private Date stockChangeTime;
    private String orderCode;
    private String outerOrderCode;
    private String skuCode;
    private String materialCode;
    private String skuName;
    private String quantity;
    private String invoiceAmount;

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

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("销售渠道/类型", "orderSource");
        tableHeader.put("日期", "stockChangeTime");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("发票金额", "invoiceAmount");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<NoInvoiceSalesDetailsTableDto> noInvoiceSalesDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (NoInvoiceSalesDetailsTableDto noInvoiceSalesDetailsTableDto : noInvoiceSalesDetailsTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSource", noInvoiceSalesDetailsTableDto.getOrderSource());
            rowData.put("stockChangeTime", noInvoiceSalesDetailsTableDto.getStockChangeTime());
            rowData.put("orderCode", noInvoiceSalesDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", noInvoiceSalesDetailsTableDto.getOuterOrderCode());
            rowData.put("skuCode", noInvoiceSalesDetailsTableDto.getSkuCode());
            rowData.put("materialCode", noInvoiceSalesDetailsTableDto.getMaterialCode());
            rowData.put("skuName", noInvoiceSalesDetailsTableDto.getSkuName());
            rowData.put("quantity", noInvoiceSalesDetailsTableDto.getQuantity());
            rowData.put("invoiceAmount", noInvoiceSalesDetailsTableDto.getInvoiceAmount());
            tableData.add(rowData);
        }
        return tableData;
    }

}
