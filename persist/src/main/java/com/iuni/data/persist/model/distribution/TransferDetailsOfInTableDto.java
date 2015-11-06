package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TransferDetailsOfInTableDto extends AbstractTableDto {

    private Date handledTime;
    private String receiveType;
    private String wareHouse;
    private String transferPartnerName;
    private String receiveCode;
    private String sku;
    private String materialCode;
    private String skuName;
    private String quantity;
    private String wareStatus;

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getTransferPartnerName() {
        return transferPartnerName;
    }

    public void setTransferPartnerName(String transferPartnerName) {
        this.transferPartnerName = transferPartnerName;
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

    public String getWareStatus() {
        return wareStatus;
    }

    public void setWareStatus(String wareStatus) {
        this.wareStatus = wareStatus;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "handledTime");
        tableHeader.put("业务类型", "receiveType");
        tableHeader.put("调入方", "wareHouse");
        tableHeader.put("调出方", "transferPartnerName");
        tableHeader.put("调拨退货单号", "receiveCode");
        tableHeader.put("SKU", "sku");
        tableHeader.put("ERP物料编号", "materialCode");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("调拨数量", "quantity");
        tableHeader.put("产品状态", "wareStatus");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<TransferDetailsOfInTableDto> transferDetailsOfInTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (TransferDetailsOfInTableDto transferDetailsOfInTableDto : transferDetailsOfInTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("handledTime", transferDetailsOfInTableDto.getHandledTime());
            rowData.put("receiveType", transferDetailsOfInTableDto.getReceiveType());
            rowData.put("wareHouse", transferDetailsOfInTableDto.getWareHouse());
            rowData.put("transferPartnerName", transferDetailsOfInTableDto.getTransferPartnerName());
            rowData.put("receiveCode", transferDetailsOfInTableDto.getReceiveCode());
            rowData.put("sku", transferDetailsOfInTableDto.getSku());
            rowData.put("materialCode", transferDetailsOfInTableDto.getMaterialCode());
            rowData.put("skuName", transferDetailsOfInTableDto.getSkuName());
            rowData.put("quantity", transferDetailsOfInTableDto.getQuantity());
            rowData.put("wareStatus", transferDetailsOfInTableDto.getWareStatus());
            tableData.add(rowData);
        }
        return tableData;
    }

}
