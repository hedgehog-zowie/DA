package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class InTransferDetailsTableDto extends AbstractTableDto {

    private Date shippingTime;
    private String transferCode;
    private String warehouseName;
    private String transferSale;
    private String skuCode;
    private String materialCode;
    private String skuName;
    private String quantity;

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getTransferCode() {
        return transferCode;
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getTransferSale() {
        return transferSale;
    }

    public void setTransferSale(String transferSale) {
        this.transferSale = transferSale;
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

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "shippingTime");
        tableHeader.put("调拔单号", "transferCode");
        tableHeader.put("调出仓位", "warehouseName");
        tableHeader.put("调入仓位", "transferSale");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("数量", "quantity");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<InTransferDetailsTableDto> inTransferDetailsTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(InTransferDetailsTableDto inTransferDetailsTableDto: inTransferDetailsTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("shippingTime", inTransferDetailsTableDto.getShippingTime());
            rowData.put("transferCode", inTransferDetailsTableDto.getTransferCode());
            rowData.put("warehouseName", inTransferDetailsTableDto.getWarehouseName());
            rowData.put("transferSale", inTransferDetailsTableDto.getTransferSale());
            rowData.put("skuCode", inTransferDetailsTableDto.getSkuCode());
            rowData.put("materialCode", inTransferDetailsTableDto.getMaterialCode());
            rowData.put("skuName", inTransferDetailsTableDto.getSkuName());
            rowData.put("quantity", inTransferDetailsTableDto.getQuantity());
            tableData.add(rowData);
        }
        return tableData;
    }

}
