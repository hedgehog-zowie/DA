package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TransferDetailsOfOutTableDto extends AbstractTableDto {

    private Date shippingTime;
    private String billType;
    private String transferName;
    private String wareHouse;
    private String transferType;
    private String transferId;
    private String quantity;
    private String sku;
    private String skuName;
    private String materialCode;
    private String outOrderCode;

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getOutOrderCode() {
        return outOrderCode;
    }

    public void setOutOrderCode(String outOrderCode) {
        this.outOrderCode = outOrderCode;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "shippingTime");
        tableHeader.put("调拨类型", "billType");
        tableHeader.put("调入方", "transferName");
        tableHeader.put("调出方", "wareHouse");
        tableHeader.put("调拨批次号", "transferId");
        tableHeader.put("外部订单号", "outOrderCode");
        tableHeader.put("SKU", "sku");
        tableHeader.put("ERP物料编号", "materialCode");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("调拨数量", "quantity");
        tableHeader.put("产品状态", "transferType");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<TransferDetailsOfOutTableDto> transferDetailsOfOutTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (TransferDetailsOfOutTableDto transferDetailsOfOutTableDto : transferDetailsOfOutTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("shippingTime", transferDetailsOfOutTableDto.getShippingTime());
            rowData.put("billType", transferDetailsOfOutTableDto.getBillType());
            rowData.put("transferName", transferDetailsOfOutTableDto.getTransferName());
            rowData.put("wareHouse", transferDetailsOfOutTableDto.getWareHouse());
            rowData.put("transferId", transferDetailsOfOutTableDto.getTransferId());
            rowData.put("outOrderCode", transferDetailsOfOutTableDto.getOutOrderCode());
            rowData.put("sku", transferDetailsOfOutTableDto.getSku());
            rowData.put("materialCode", transferDetailsOfOutTableDto.getMaterialCode());
            rowData.put("skuName", transferDetailsOfOutTableDto.getSkuName());
            rowData.put("quantity", transferDetailsOfOutTableDto.getQuantity());
            rowData.put("transferType", transferDetailsOfOutTableDto.getTransferType());
            tableData.add(rowData);
        }
        return tableData;
    }

}
