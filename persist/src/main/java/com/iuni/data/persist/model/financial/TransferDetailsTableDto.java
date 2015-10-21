package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TransferDetailsTableDto extends AbstractTableDto {

    private Date shippingTime;
    private String transferId;
    private String warehouse;
    private String consignee;
    private String transferTo;
    private String contact;
    private String skuName;
    private String quantity;
    private String measureUnit;
    private String logisticNo;
    private String status;
    private String transferSend;
    private String returnNum;

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getLogisticNo() {
        return logisticNo;
    }

    public void setLogisticNo(String logisticNo) {
        this.logisticNo = logisticNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferSend() {
        return transferSend;
    }

    public void setTransferSend(String transferSend) {
        this.transferSend = transferSend;
    }

    public String getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(String returnNum) {
        this.returnNum = returnNum;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("发货时间", "shippingTime");
        tableHeader.put("调拨批次号", "transferId");
        tableHeader.put("发货仓", "warehouse");
        tableHeader.put("收货人", "consignee");
        tableHeader.put("收货地址", "transferTo");
        tableHeader.put("联系电话", "contact");
        tableHeader.put("SKU名称", "skuName");
        tableHeader.put("数量", "quantity");
        tableHeader.put("单位", "measureUnit");
        tableHeader.put("物流单号", "logisticNo");
        tableHeader.put("调货状态", "status");
        tableHeader.put("目的地", "transferSend");
        tableHeader.put("已退货数量", "returnNum");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<TransferDetailsTableDto> transferDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(TransferDetailsTableDto transferDetailsTableDto: transferDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("shippingTime", transferDetailsTableDto.getShippingTime());
            rowData.put("transferId", transferDetailsTableDto.getTransferId());
            rowData.put("warehouse", transferDetailsTableDto.getWarehouse());
            rowData.put("consignee", transferDetailsTableDto.getConsignee());
            rowData.put("transferTo", transferDetailsTableDto.getTransferTo());
            rowData.put("contact", transferDetailsTableDto.getContact());
            rowData.put("skuName", transferDetailsTableDto.getSkuName());
            rowData.put("quantity", transferDetailsTableDto.getQuantity());
            rowData.put("measureUnit", transferDetailsTableDto.getMeasureUnit());
            rowData.put("logisticNo", transferDetailsTableDto.getLogisticNo());
            rowData.put("status", transferDetailsTableDto.getStatus());
            rowData.put("transferSend", transferDetailsTableDto.getTransferSend());
            rowData.put("returnNum", transferDetailsTableDto.getReturnNum());
            tableData.add(rowData);
        }
        return tableData;
    }

}
