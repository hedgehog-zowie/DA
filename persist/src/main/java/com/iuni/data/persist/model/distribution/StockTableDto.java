package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockTableDto extends AbstractTableDto {

    private Date time;
    private String wareHouse;
    private String skuCode;
    private String goodsName;
    private String skuName;
    private String materialCode;
    private String measureUnit;
    private String inStockQty;
    private String outStockQty;
    private String endStockQty;
    private String endNonDefeQty;
    private String endDefeQty;
    private String occupyStockQty;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getInStockQty() {
        return inStockQty;
    }

    public void setInStockQty(String inStockQty) {
        this.inStockQty = inStockQty;
    }

    public String getOutStockQty() {
        return outStockQty;
    }

    public void setOutStockQty(String outStockQty) {
        this.outStockQty = outStockQty;
    }

    public String getEndStockQty() {
        return endStockQty;
    }

    public void setEndStockQty(String endStockQty) {
        this.endStockQty = endStockQty;
    }

    public String getEndNonDefeQty() {
        return endNonDefeQty;
    }

    public void setEndNonDefeQty(String endNonDefeQty) {
        this.endNonDefeQty = endNonDefeQty;
    }

    public String getEndDefeQty() {
        return endDefeQty;
    }

    public void setEndDefeQty(String endDefeQty) {
        this.endDefeQty = endDefeQty;
    }

    public String getOccupyStockQty() {
        return occupyStockQty;
    }

    public void setOccupyStockQty(String occupyStockQty) {
        this.occupyStockQty = occupyStockQty;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("仓库", "wareHouse");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("物料编码", "materialCode");
        tableHeader.put("规格型号", "skuName");
        tableHeader.put("单位", "measureUnit");
        tableHeader.put("良品", "acceptedGoods");
        tableHeader.put("次品", "defectiveGoods");
        tableHeader.put("库存合计", "totalGoods");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockTableDto> stockTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (StockTableDto stockTableDto : stockTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date",stockTableDto.getTime());
            rowData.put("wareHouse",stockTableDto.getWareHouse());
            rowData.put("skuCode",stockTableDto.getSkuCode());
            rowData.put("materialCode",stockTableDto.getMaterialCode());
            rowData.put("skuName",stockTableDto.getSkuName());
            rowData.put("measureUnit",stockTableDto.getMeasureUnit());
            tableData.add(rowData);
        }
        return tableData;
    }

}
