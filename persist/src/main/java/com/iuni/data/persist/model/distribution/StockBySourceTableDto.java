package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockBySourceTableDto extends AbstractTableDto {

    private String date;
    private String wareHouseName;
    private String sourceName;
    private String bizType;
    private String skuCode;
    private String catName;
    private String skuName;
    private String materialCode;
    private String measureUnit;
    private String quantity;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
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

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("仓库", "wareHouseName");
        tableHeader.put("订单来源", "sourceName");
        tableHeader.put("业务类型", "bizType");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("商品类型", "catName");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("ERP物料编码", "materialCode");
        tableHeader.put("单位", "measureUnit");
        tableHeader.put("数量", "quantity");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockBySourceTableDto> stockBySourceTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(StockBySourceTableDto stockBySourceTableDto : stockBySourceTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date", stockBySourceTableDto.getDate());
            rowData.put("wareHouseName", stockBySourceTableDto.getWareHouseName());
            rowData.put("sourceName", stockBySourceTableDto.getSourceName());
            rowData.put("bizType", stockBySourceTableDto.getBizType());
            rowData.put("skuCode", stockBySourceTableDto.getSkuCode());
            rowData.put("catName", stockBySourceTableDto.getCatName());
            rowData.put("skuName", stockBySourceTableDto.getSkuName());
            rowData.put("materialCode", stockBySourceTableDto.getMaterialCode());
            rowData.put("measureUnit", stockBySourceTableDto.getMeasureUnit());
            rowData.put("quantity", stockBySourceTableDto.getQuantity());
            tableData.add(rowData);
        }
        return tableData;
    }

}
