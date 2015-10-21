package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PayAmountCheckTableDto extends AbstractTableDto {

    private Date stockTime;
    private String orderCode;
    private String parentOrderId;
    private String outerOrderCode;
    private String orderSource;
    private String goodsAmount;
    private String bonus;
    private String shippingFee;
    private String orderAmount;
    private String paySerialNo;
    private String paidAmount;
    private String payName;
    private String invoiceAmount;
    private String deductAmount;
    private String deductReason;

    public Date getStockTime() {
        return stockTime;
    }

    public void setStockTime(Date stockTime) {
        this.stockTime = stockTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaySerialNo() {
        return paySerialNo;
    }

    public void setPaySerialNo(String paySerialNo) {
        this.paySerialNo = paySerialNo;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getDeductReason() {
        return deductReason;
    }

    public void setDeductReason(String deductReason) {
        this.deductReason = deductReason;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("出入库日期", "stockTime");
        tableHeader.put("子订单号", "orderCode");
        tableHeader.put("主订单号", "parentOrderId");
        tableHeader.put("外部订单号", "outerOrderCode");
        tableHeader.put("销售渠道/类型", "orderSource");
        tableHeader.put("商品金额", "goodsAmount");
        tableHeader.put("优惠金额", "bonus");
        tableHeader.put("代收快递费", "shippingFee");
        tableHeader.put("订单金额", "orderAmount");
        tableHeader.put("支付流水号", "paySerialNo");
        tableHeader.put("收款金额", "paidAmount");
        tableHeader.put("收款方式", "payName");
        tableHeader.put("发票金额", "invoiceAmount");
        tableHeader.put("退货扣款金额", "deductAmount");
        tableHeader.put("退货扣款原因", "deductReason");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<PayAmountCheckTableDto> payAmountCheckTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (PayAmountCheckTableDto payAmountCheckTableDto : payAmountCheckTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("出入库日期", payAmountCheckTableDto.getStockTime());
            rowData.put("子订单号", payAmountCheckTableDto.getOrderCode());
            rowData.put("主订单号", payAmountCheckTableDto.getParentOrderId());
            rowData.put("外部订单号", payAmountCheckTableDto.getOuterOrderCode());
            rowData.put("销售渠道/类型", payAmountCheckTableDto.getOrderSource());
            rowData.put("商品金额", payAmountCheckTableDto.getGoodsAmount());
            rowData.put("优惠金额", payAmountCheckTableDto.getBonus());
            rowData.put("代收快递费", payAmountCheckTableDto.getShippingFee());
            rowData.put("订单金额", payAmountCheckTableDto.getOrderAmount());
            rowData.put("支付流水号", payAmountCheckTableDto.getPaySerialNo());
            rowData.put("收款金额", payAmountCheckTableDto.getPaidAmount());
            rowData.put("收款方式", payAmountCheckTableDto.getPayName());
            rowData.put("发票金额", payAmountCheckTableDto.getInvoiceAmount());
            rowData.put("退货扣款金额", payAmountCheckTableDto.getDeductAmount());
            rowData.put("退货扣款原因", payAmountCheckTableDto.getDeductReason());
            tableData.add(rowData);
        }
        return tableData;
    }

}
