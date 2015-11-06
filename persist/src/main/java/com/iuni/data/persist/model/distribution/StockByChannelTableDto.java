package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class StockByChannelTableDto extends AbstractTableDto {

    private String date;
    private String sourceName;
    private String skuCode;
    private String catName;
    private String skuName;
    private String materialCode;
    private String measureUnit;
    /**
     * 发货
     */
    private Integer send;
    /**
     * 退货
     */
    private Integer back;
    /**
     * 换货退回
     */
    private Integer backOfExchange;
    /**
     * 拒收
     */
    private Integer reject;
    /**
     * 刷单退回
     */
    private Integer backOfScalping;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getBack() {
        return back;
    }

    public void setBack(Integer back) {
        this.back = back;
    }

    public Integer getBackOfExchange() {
        return backOfExchange;
    }

    public void setBackOfExchange(Integer backOfExchange) {
        this.backOfExchange = backOfExchange;
    }

    public Integer getReject() {
        return reject;
    }

    public void setReject(Integer reject) {
        this.reject = reject;
    }

    public Integer getBackOfScalping() {
        return backOfScalping;
    }

    public void setBackOfScalping(Integer backOfScalping) {
        this.backOfScalping = backOfScalping;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("渠道", "sourceName");
        tableHeader.put("SKU", "skuCode");
        tableHeader.put("商品类型", "catName");
        tableHeader.put("名称规格", "skuName");
        tableHeader.put("ERP物料编码", "materialCode");
        tableHeader.put("单位", "measureUnit");
        tableHeader.put("发货", "send");
        tableHeader.put("退货", "back");
        tableHeader.put("换货退回", "backOfExchange");
        tableHeader.put("拒收", "reject");
        tableHeader.put("刷单退回", "backOfScalping");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<StockByChannelTableDto> stockByChannelTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(StockByChannelTableDto stockByChannelTableDto : stockByChannelTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date", stockByChannelTableDto.getDate());
            rowData.put("sourceName", stockByChannelTableDto.getSourceName());
            rowData.put("skuCode", stockByChannelTableDto.getSkuCode());
            rowData.put("catName", stockByChannelTableDto.getCatName());
            rowData.put("skuName", stockByChannelTableDto.getSkuName());
            rowData.put("materialCode", stockByChannelTableDto.getMaterialCode());
            rowData.put("measureUnit", stockByChannelTableDto.getMeasureUnit());
            rowData.put("send", stockByChannelTableDto.getSend());
            rowData.put("back", stockByChannelTableDto.getBack());
            rowData.put("backOfExchange", stockByChannelTableDto.getBackOfExchange());
            rowData.put("reject", stockByChannelTableDto.getReject());
            rowData.put("backOfScalping", stockByChannelTableDto.getBackOfScalping());
            tableData.add(rowData);
        }
        return tableData;
    }

}
