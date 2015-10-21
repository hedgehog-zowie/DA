package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SalesOrderDetailsTableDto extends AbstractTableDto {

    private Date shippingTime;
    private String orderSource;
    private String batchCode;
    private String orderCode;
    private String outerOrderCode;
    private String consignee;
    private String shippingAddress;
    private String mobile;
    private String payType;
    private String shippingName;
    private String shippingNo;
    private Date orderTime;
    private String payNo;
    private String orderAmount;
    private String payAmount;
    private String paidAmount;
    private String invoiceEnabled;
    private String invoiceTitle;
    private String invoiceAmount;
    private String orderStatus;
    private Date signedTime;
    private String skuName;
    private String quantity;
    private String unitPrice;
    private String goodsAmount;
    private String weight;

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getInvoiceEnabled() {
        return invoiceEnabled;
    }

    public void setInvoiceEnabled(String invoiceEnabled) {
        this.invoiceEnabled = invoiceEnabled;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("发货时间", "shippingTime");
        tableHeader.put("订单来源", "orderSource");
        tableHeader.put("拣货批次", "batchCode");
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("收货人", "consignee");
        tableHeader.put("收货地址", "shippingAddress");
        tableHeader.put("联系电话", "mobile");
        tableHeader.put("付款方式", "payType");
        tableHeader.put("快递类型", "shippingName");
        tableHeader.put("运单号", "shippingNo");
        tableHeader.put("下单时间", "orderTime");
        tableHeader.put("交易号", "payNo");
        tableHeader.put("订单金额", "orderAmount");
        tableHeader.put("应付金额", "payAmount");
        tableHeader.put("已支付金额", "paidAmount");
        tableHeader.put("是否需要发票", "invoiceEnabled");
        tableHeader.put("发票抬头", "invoiceTitle");
        tableHeader.put("发票价格", "invoiceAmount");
        tableHeader.put("订单状态", "orderStatus");
        tableHeader.put("签收日期", "signedTime");
        tableHeader.put("SKU名称", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("商品单价", "unitPrice");
        tableHeader.put("商品总价", "goodsAmount");
        tableHeader.put("重量", "weight");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<SalesOrderDetailsTableDto> salesOrderDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(SalesOrderDetailsTableDto salesOrderDetailsTableDto:salesOrderDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("shippingTime", salesOrderDetailsTableDto.getShippingTime());
            rowData.put("orderSource", salesOrderDetailsTableDto.getOrderSource());
            rowData.put("batchCode", salesOrderDetailsTableDto.getBatchCode());
            rowData.put("orderCode", salesOrderDetailsTableDto.getOrderCode());
            rowData.put("outerOrderCode", salesOrderDetailsTableDto.getOuterOrderCode());
            rowData.put("consignee", salesOrderDetailsTableDto.getConsignee());
            rowData.put("shippingAddress", salesOrderDetailsTableDto.getShippingAddress());
            rowData.put("mobile", salesOrderDetailsTableDto.getMobile());
            rowData.put("payType", salesOrderDetailsTableDto.getPayType());
            rowData.put("shippingName", salesOrderDetailsTableDto.getShippingName());
            rowData.put("shippingNo", salesOrderDetailsTableDto.getShippingNo());
            rowData.put("orderTime", salesOrderDetailsTableDto.getOrderTime());
            rowData.put("payNo", salesOrderDetailsTableDto.getPayNo());
            rowData.put("orderAmount", salesOrderDetailsTableDto.getOrderAmount());
            rowData.put("payAmount", salesOrderDetailsTableDto.getPaidAmount());
            rowData.put("paidAmount", salesOrderDetailsTableDto.getPaidAmount());
            rowData.put("invoiceEnabled", salesOrderDetailsTableDto.getInvoiceEnabled());
            rowData.put("invoiceTitle", salesOrderDetailsTableDto.getInvoiceTitle());
            rowData.put("invoiceAmount", salesOrderDetailsTableDto.getInvoiceAmount());
            rowData.put("orderStatus", salesOrderDetailsTableDto.getOrderStatus());
            rowData.put("signedTime", salesOrderDetailsTableDto.getSignedTime());
            rowData.put("skuName", salesOrderDetailsTableDto.getSkuName());
            rowData.put("quantity", salesOrderDetailsTableDto.getQuantity());
            rowData.put("unitPrice", salesOrderDetailsTableDto.getUnitPrice());
            rowData.put("goodsAmount", salesOrderDetailsTableDto.getGoodsAmount());
            rowData.put("weight", salesOrderDetailsTableDto.getWeight());
            tableData.add(rowData);
        }
        return tableData;
    }

}
