package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockMoveDetailsTableDto extends AbstractTableDto {

    private Date time;
    private String orderSource;
    private String orderType;
    private String sku;
    private String waresName;
    private String skuName;
    private String materialCode;
    private String orderCode;
    private String outerOrderCode;
    private String payNo;
    private String deliveryCode;
    private String price;
    private String quantity;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWaresName() {
        return waresName;
    }

    public void setWaresName(String waresName) {
        this.waresName = waresName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "time");
        tableHeader.put("销售渠道/类型", "orderSource");
        tableHeader.put("订单类型", "orderType");
        tableHeader.put("SKU", "sku");
        tableHeader.put("商品类型", "waresName");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("支付流水号", "payNo");
        tableHeader.put("出库单号", "deliveryCode");
        tableHeader.put("单价", "price");
        tableHeader.put("数量", "quantity");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockMoveDetailsTableDto> stockMoveDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (StockMoveDetailsTableDto stockMoveDetailsTableDto : stockMoveDetailsTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("time", stockMoveDetailsTableDto.getTime());
            rowData.put("orderSource", stockMoveDetailsTableDto.getOrderSource());
            rowData.put("orderType", stockMoveDetailsTableDto.getOrderType());
            rowData.put("sku", stockMoveDetailsTableDto.getSku());
            rowData.put("waresName", stockMoveDetailsTableDto.getWaresName());
            rowData.put("skuName", stockMoveDetailsTableDto.getSkuName());
            rowData.put("materialCode", stockMoveDetailsTableDto.getMaterialCode());
            rowData.put("orderCode", stockMoveDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", stockMoveDetailsTableDto.getOuterOrderCode());
            rowData.put("payNo", stockMoveDetailsTableDto.getPayNo());
            rowData.put("deliveryCode", stockMoveDetailsTableDto.getDeliveryCode());
            rowData.put("price", stockMoveDetailsTableDto.getPrice());
            rowData.put("quantity", stockMoveDetailsTableDto.getQuantity());
            tableData.add(rowData);
        }
        return tableData;
    }

}
