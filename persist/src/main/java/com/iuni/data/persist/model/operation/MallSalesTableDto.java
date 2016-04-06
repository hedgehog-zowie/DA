package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * 商场销售结果
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class MallSalesTableDto extends AbstractTableDto {

    /**
     * 日期
     */
    private String date;
    /**
     * 订单来源
     */
    private String referer;
    /**
     * 下单总数(含无效订单)
     */
    private long totalOrderNum;
    /**
     * 下单总金额
     */
    private double totalOrderAmount;
    /**
     * 下单商品总件数
     */
    private long totalGoodsNum;
    /**
     * 在线支付下单数
     */
    private long onlinePayOrderNum;
    /**
     * 货到付款下单数
     */
    private long offlinePayOrderNum;
    /**
     * 退货订单数
     */
    private long returnedOrderNum;
    /**
     * 换货订单数
     */
    private long exchangeOrderNum;
    /**
     * 维修订单数
     */
    private long repairOrderNum;
    /**
     * 拒收订单数
     */
    private long refusedOrderNum;
    /**
     * 有效订单数(已在线支付+已审核货到付款)
     */
    private long validOrderNum;
    /**
     * 有效订单金额
     */
    private double validOrderAmount;
    /**
     * 有效订单商品总件数
     */
    private long validGoodsNum;
    /**
     * 有效订单比率
     */
    private double validOrderRate;
    /**
     * 客单价
     */
    private double unitPrice;
    /**
     * 客件数
     */
    private long unitNum;
    /**
     * 已支付订单数(已在线支付)
     */
    private long paidOrderNum;
    /**
     * 货到付款有效订单数(已审核)
     */
    private long offlineValidOrderNum;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public long getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(long totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public long getTotalGoodsNum() {
        return totalGoodsNum;
    }

    public void setTotalGoodsNum(long totalGoodsNum) {
        this.totalGoodsNum = totalGoodsNum;
    }

    public long getOnlinePayOrderNum() {
        return onlinePayOrderNum;
    }

    public void setOnlinePayOrderNum(long onlinePayOrderNum) {
        this.onlinePayOrderNum = onlinePayOrderNum;
    }

    public long getOfflinePayOrderNum() {
        return offlinePayOrderNum;
    }

    public void setOfflinePayOrderNum(long offlinePayOrderNum) {
        this.offlinePayOrderNum = offlinePayOrderNum;
    }

    public long getReturnedOrderNum() {
        return returnedOrderNum;
    }

    public void setReturnedOrderNum(long returnedOrderNum) {
        this.returnedOrderNum = returnedOrderNum;
    }

    public long getExchangeOrderNum() {
        return exchangeOrderNum;
    }

    public void setExchangeOrderNum(long exchangeOrderNum) {
        this.exchangeOrderNum = exchangeOrderNum;
    }

    public long getRepairOrderNum() {
        return repairOrderNum;
    }

    public void setRepairOrderNum(long repairOrderNum) {
        this.repairOrderNum = repairOrderNum;
    }

    public long getRefusedOrderNum() {
        return refusedOrderNum;
    }

    public void setRefusedOrderNum(long refusedOrderNum) {
        this.refusedOrderNum = refusedOrderNum;
    }

    public long getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(long validOrderNum) {
        this.validOrderNum = validOrderNum;
    }

    public double getValidOrderAmount() {
        return validOrderAmount;
    }

    public void setValidOrderAmount(double validOrderAmount) {
        this.validOrderAmount = validOrderAmount;
    }

    public long getValidGoodsNum() {
        return validGoodsNum;
    }

    public void setValidGoodsNum(long validGoodsNum) {
        this.validGoodsNum = validGoodsNum;
    }

    public double getValidOrderRate() {
        return validOrderRate;
    }

    public void setValidOrderRate(double validOrderRate) {
        this.validOrderRate = validOrderRate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(long unitNum) {
        this.unitNum = unitNum;
    }

    public long getPaidOrderNum() {
        return paidOrderNum;
    }

    public void setPaidOrderNum(long paidOrderNum) {
        this.paidOrderNum = paidOrderNum;
    }

    public long getOfflineValidOrderNum() {
        return offlineValidOrderNum;
    }

    public void setOfflineValidOrderNum(long offlineValidOrderNum) {
        this.offlineValidOrderNum = offlineValidOrderNum;
    }

    /**
     * 生成表头
     *
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("下单总数(含无效订单)", "totalOrderNum");
        tableHeader.put("下单总金额", "totalOrderAmount");
        tableHeader.put("下单商品总件数", "totalGoodsNum");
        tableHeader.put("在线支付下单数", "onlinePayOrderNum");
        tableHeader.put("货到付款下单数", "offlinePayOrderNum");
        tableHeader.put("退货订单数", "returnedOrderNum");
        tableHeader.put("换货订单数", "exchangeOrderNum");
        tableHeader.put("维修订单数", "repairOrderNum");
        tableHeader.put("拒收订单数", "refusedOrderNum");
        tableHeader.put("有效订单数(已在线支付+已审核货到付款)", "validOrderNum");
        tableHeader.put("有效订单金额", "validOrderAmount");
        tableHeader.put("有效订单商品总件数", "validGoodsNum");
        tableHeader.put("有效订单比率", "validOrderRate");
        tableHeader.put("客单价", "unitPrice");
        tableHeader.put("客件数", "unitNum");
        tableHeader.put("已支付订单数(已在线支付)", "paidOrderNum");
        tableHeader.put("货到付款有效订单数(已审核)", "offlineValidOrderNum");
        return tableHeader;
    }

    /**
     * 生成表数据
     *
     * @param mallSalesTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<MallSalesTableDto> mallSalesTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (MallSalesTableDto mallSalesTableDto : mallSalesTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date", mallSalesTableDto.getDate());
            rowData.put("totalOrderNum", mallSalesTableDto.getTotalOrderNum());
            rowData.put("totalOrderAmount", mallSalesTableDto.getTotalOrderAmount());
            rowData.put("totalGoodsNum", mallSalesTableDto.getTotalGoodsNum());
            rowData.put("onlinePayOrderNum", mallSalesTableDto.getOnlinePayOrderNum());
            rowData.put("offlinePayOrderNum", mallSalesTableDto.getOfflinePayOrderNum());
            rowData.put("returnedOrderNum", mallSalesTableDto.getReturnedOrderNum());
            rowData.put("exchangeOrderNum", mallSalesTableDto.getExchangeOrderNum());
            rowData.put("repairOrderNum", mallSalesTableDto.getRepairOrderNum());
            rowData.put("refusedOrderNum", mallSalesTableDto.getRefusedOrderNum());
            rowData.put("validOrderNum", mallSalesTableDto.getValidOrderNum());
            rowData.put("validOrderAmount", mallSalesTableDto.getValidOrderAmount());
            rowData.put("validGoodsNum", mallSalesTableDto.getValidGoodsNum());
            rowData.put("validOrderRate", mallSalesTableDto.getValidOrderRate());
            rowData.put("unitPrice", mallSalesTableDto.getUnitPrice());
            rowData.put("unitNum", mallSalesTableDto.getUnitNum());
            rowData.put("paidOrderNum", mallSalesTableDto.getPaidOrderNum());
            rowData.put("offlineValidOrderNum", mallSalesTableDto.getOfflineValidOrderNum());
            tableData.add(rowData);
        }
        return tableData;
    }

}
