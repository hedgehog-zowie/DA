package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class BigOrderDetailsTableDto extends AbstractTableDto{

    private String tid;
    private String companyName;
    private String receiverAddress;
    private Double totalFee;
    private String auditStatus;
    private String paymentStatus;
    private String shippingStatus;
    private String paymentConfirmed;
    private Date addTime;
    private Date shippingTime;
    private Date paymentConfirmTime;
    private String skuCode;
    private String skuName;
    private String materialCode;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setPaymentConfirmed(String paymentConfirmed) {
        this.paymentConfirmed = paymentConfirmed;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Date getPaymentConfirmTime() {
        return paymentConfirmTime;
    }

    public void setPaymentConfirmTime(Date paymentConfirmTime) {
        this.paymentConfirmTime = paymentConfirmTime;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单编码", "tid");
        tableHeader.put("公司名称", "companyName");
        tableHeader.put("收货地址", "receiverAddress");
        tableHeader.put("订单金额", "totalFee");
        tableHeader.put("订单状态", "auditStatus");
        tableHeader.put("支付状态", "paymentStatus");
        tableHeader.put("物流状态", "shippingStatus");
        tableHeader.put("确认金额", "paymentConfirmed");
        tableHeader.put("创建时间", "addTime");
        tableHeader.put("发货时间", "shippingTime");
        tableHeader.put("结算时间", "paymentConfirmTime");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("物料编码", "materialCode");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<BigOrderDetailsTableDto> bigOrderDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(BigOrderDetailsTableDto bigOrderDetailsTableDto : bigOrderDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("tid", bigOrderDetailsTableDto.getTid());
            rowData.put("companyName", bigOrderDetailsTableDto.getCompanyName());
            rowData.put("receiverAddress", bigOrderDetailsTableDto.getReceiverAddress());
            rowData.put("totalFee", bigOrderDetailsTableDto.getTotalFee());
            rowData.put("auditStatus", bigOrderDetailsTableDto.getAuditStatus());
            rowData.put("paymentStatus", bigOrderDetailsTableDto.getPaymentStatus());
            rowData.put("shippingStatus", bigOrderDetailsTableDto.getShippingStatus());
            rowData.put("paymentConfirmed", bigOrderDetailsTableDto.getPaymentConfirmed());
            rowData.put("addTime", bigOrderDetailsTableDto.getAddTime());
            rowData.put("shippingTime", bigOrderDetailsTableDto.getShippingTime());
            rowData.put("paymentConfirmTime", bigOrderDetailsTableDto.getPaymentConfirmTime());
            rowData.put("skuCode", bigOrderDetailsTableDto.getSkuCode());
            rowData.put("skuName", bigOrderDetailsTableDto.getSkuName());
            rowData.put("materialCode", bigOrderDetailsTableDto.getMaterialCode());
            tableData.add(rowData);
        }
        return tableData;
    }

}
