package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SalesTableDto extends AbstractTableDto {

    /**
     * 时间区间
     */
    private String range;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 销售渠道
     */
    private String orderSource;
    /**
     * 规格型号
     */
    private String goodsName;
    /**
     * 商品名称
     */
    private String wareName;
    /**
     * SKU编码
     */
    private String sku;
    /**
     * 数量
     */
    private Long num;
    /**
     * 金额
     */
    private Double salePrice;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("时间区间", "range");
        tableHeader.put("支付时间", "payTime");
        tableHeader.put("销售渠道", "orderSource");
        tableHeader.put("规格型号", "goodsName");
        tableHeader.put("商品", "wareName");
        tableHeader.put("SKU", "sku");
        tableHeader.put("数量", "num");
        tableHeader.put("支付金额", "salePrice");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<SalesTableDto> salesTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(SalesTableDto salesTableDto : salesTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("range", salesTableDto.getRange());
            rowData.put("payTime", salesTableDto.getPayTime());
            rowData.put("orderSource", salesTableDto.getOrderSource());
            rowData.put("goodsName", salesTableDto.getGoodsName());
            rowData.put("wareName", salesTableDto.getWareName());
            rowData.put("sku", salesTableDto.getSku());
            rowData.put("num", salesTableDto.getNum());
            rowData.put("salePrice", salesTableDto.getSalePrice());
            tableData.add(rowData);
        }
        return tableData;
    }

}
