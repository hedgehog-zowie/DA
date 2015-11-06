package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockDetailsTableDto extends AbstractTableDto {

    private Date date;
    private String wareHouse;
    private String skuCode;
    private String materialCode;
    private String skuName;
    private String measureUnit;
    private String acceptedGoods;
    private String defectiveGoods;
    private String totalGoods;

    public Date getDate() {
        return date;
    }

    public void setDate(Date time) {
        this.date = time;
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

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getAcceptedGoods() {
        return acceptedGoods;
    }

    public void setAcceptedGoods(String acceptedGoods) {
        this.acceptedGoods = acceptedGoods;
    }

    public String getDefectiveGoods() {
        return defectiveGoods;
    }

    public void setDefectiveGoods(String defectiveGoods) {
        this.defectiveGoods = defectiveGoods;
    }

    public String getTotalGoods() {
        return totalGoods;
    }

    public void setTotalGoods(String totalGoods) {
        this.totalGoods = totalGoods;
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

    public static List<Map<String, Object>> generateTableData(List<StockDetailsTableDto> stockTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (StockDetailsTableDto stockTableDto : stockTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date",stockTableDto.getDate());
            rowData.put("wareHouse",stockTableDto.getWareHouse());
            rowData.put("skuCode",stockTableDto.getSkuCode());
            rowData.put("materialCode",stockTableDto.getMaterialCode());
            rowData.put("skuName",stockTableDto.getSkuName());
            rowData.put("measureUnit",stockTableDto.getMeasureUnit());
            rowData.put("acceptedGoods",stockTableDto.getAcceptedGoods());
            rowData.put("defectiveGoods",stockTableDto.getDefectiveGoods());
            rowData.put("totalGoods",stockTableDto.getTotalGoods());
            tableData.add(rowData);
        }
        return tableData;
    }

}
