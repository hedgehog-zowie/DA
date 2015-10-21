package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ProcurementDetailsTableDto extends AbstractTableDto {

    private Date time;
    private String supplierName;
    private String procurementSn;
    private String receiveCode;
    private String sku;
    private String  materialCode;
    private String skuName;
    private String quantity;
    private String remark;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProcurementSn() {
        return procurementSn;
    }

    public void setProcurementSn(String procurementSn) {
        this.procurementSn = procurementSn;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("入库日期", "time");
        tableHeader.put("供应商", "supplierName");
        tableHeader.put("采购订单号", "procurementSn");
        tableHeader.put("入库单号", "receiveCode");
        tableHeader.put("SKU", "sku");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("备注", "remark");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<ProcurementDetailsTableDto> procurementDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(ProcurementDetailsTableDto procurementDetailsTableDto: procurementDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("入库日期", procurementDetailsTableDto.getTime());
            rowData.put("供应商", procurementDetailsTableDto.getSupplierName());
            rowData.put("采购订单号", procurementDetailsTableDto.getProcurementSn());
            rowData.put("入库单号", procurementDetailsTableDto.getReceiveCode());
            rowData.put("SKU", procurementDetailsTableDto.getSku());
            rowData.put("物料编码", procurementDetailsTableDto.getMaterialCode());
            rowData.put("名称规格", procurementDetailsTableDto.getSkuName());
            rowData.put("数量", procurementDetailsTableDto.getQuantity());
            rowData.put("备注", procurementDetailsTableDto.getRemark());
            tableData.add(rowData);
        }
        return tableData;
    }

}
