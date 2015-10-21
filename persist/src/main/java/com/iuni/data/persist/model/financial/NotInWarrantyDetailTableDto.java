package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class NotInWarrantyDetailTableDto extends AbstractTableDto {

    private Date payConfirmTime;
    private String payer;
    private String repairsSn;
    private String userName;
    private String mobile;
    private String imei;
    private String materialCode;
    private String materialName;
    private String quantity;
    private String materialFee;
    private String handFee;
    private String invoiceTime;
    private String invoiceCode;
    private String fee;
    private String remark;

    public Date getPayConfirmTime() {
        return payConfirmTime;
    }

    public void setPayConfirmTime(Date payConfirmTime) {
        this.payConfirmTime = payConfirmTime;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getRepairsSn() {
        return repairsSn;
    }

    public void setRepairsSn(String repairsSn) {
        this.repairsSn = repairsSn;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMaterialFee() {
        return materialFee;
    }

    public void setMaterialFee(String materialFee) {
        this.materialFee = materialFee;
    }

    public String getHandFee() {
        return handFee;
    }

    public void setHandFee(String handFee) {
        this.handFee = handFee;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("收款日期", "payConfirmTime");
        tableHeader.put("付款人名称", "payer");
        tableHeader.put("维修单编号", "repairsSn");
        tableHeader.put("客户名称", "userName");
        tableHeader.put("客户电话", "mobile");
        tableHeader.put("IEMI号", "imei");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("物料名称", "materialName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("物料金额", "materialFee");
        tableHeader.put("维修等级金额", "handFee");
        tableHeader.put("开票日期", "invoiceTime");
        tableHeader.put("发票号码", "invoiceCode");
        tableHeader.put("合计金额", "fee");
        tableHeader.put("备注", "remark");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<NotInWarrantyDetailTableDto> notInWarrantyDetailTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(NotInWarrantyDetailTableDto notInWarrantyDetailTableDto: notInWarrantyDetailTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("payConfirmTime", notInWarrantyDetailTableDto.getPayConfirmTime());
            rowData.put("payer", notInWarrantyDetailTableDto.getPayer());
            rowData.put("repairsSn", notInWarrantyDetailTableDto.getRepairsSn());
            rowData.put("userName", notInWarrantyDetailTableDto.getUserName());
            rowData.put("mobile", notInWarrantyDetailTableDto.getMobile());
            rowData.put("imei", notInWarrantyDetailTableDto.getImei());
            rowData.put("materialCode", notInWarrantyDetailTableDto.getMaterialCode());
            rowData.put("materialName", notInWarrantyDetailTableDto.getMaterialName());
            rowData.put("quantity", notInWarrantyDetailTableDto.getQuantity());
            rowData.put("materialFee", notInWarrantyDetailTableDto.getMaterialFee());
            rowData.put("handFee", notInWarrantyDetailTableDto.getHandFee());
            rowData.put("invoiceTime", notInWarrantyDetailTableDto.getInvoiceTime());
            rowData.put("invoiceCode", notInWarrantyDetailTableDto.getInvoiceCode());
            rowData.put("fee", notInWarrantyDetailTableDto.getFee());
            rowData.put("remark", notInWarrantyDetailTableDto.getRemark());
            tableData.add(rowData);
        }
        return tableData;
    }
}
