package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockTableDto extends AbstractTableDto {

    private String time;
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
    /**
     * 采购入库
     */
    private String procurementIn;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getProcurementIn() {
        return procurementIn;
    }

    public void setProcurementIn(String procurementIn) {
        this.procurementIn = procurementIn;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "time");
        tableHeader.put("仓库", "wareHouse");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("商品类型", "goodsName");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("ERP物料编码", "materialCode");
        tableHeader.put("单位", "measureUnit");
        tableHeader.put("入库", "inStockQty");
        tableHeader.put("采购入库", "procurementIn");
        tableHeader.put("出库", "outStockQty");
        tableHeader.put("总库存", "endStockQty");
        tableHeader.put("可销库存", "endNonDefeQty");
        tableHeader.put("不可销库存", "endDefeQty");
        tableHeader.put("占用库存", "occupyStockQty");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockTableDto> stockTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (StockTableDto stockTableDto : stockTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("time",stockTableDto.getTime());
            rowData.put("wareHouse",stockTableDto.getWareHouse());
            rowData.put("skuCode",stockTableDto.getSkuCode());
            rowData.put("goodsName",stockTableDto.getGoodsName());
            rowData.put("skuName",stockTableDto.getSkuName());
            rowData.put("materialCode",stockTableDto.getMaterialCode());
            rowData.put("measureUnit",stockTableDto.getMeasureUnit());
            rowData.put("inStockQty",stockTableDto.getInStockQty());
            rowData.put("procurementIn",stockTableDto.getProcurementIn());
            rowData.put("outStockQty",stockTableDto.getOutStockQty());
            rowData.put("endStockQty",stockTableDto.getEndStockQty());
            rowData.put("endNonDefeQty",stockTableDto.getEndNonDefeQty());
            rowData.put("endDefeQty",stockTableDto.getEndDefeQty());
            rowData.put("occupyStockQty",stockTableDto.getOccupyStockQty());
            tableData.add(rowData);
        }
        return tableData;
    }

}
