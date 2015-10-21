package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class WeChatPayTableDto extends AbstractTableDto {
    private String tradeDate;
    private String weChatOrderSn;
    private String mchOrderSn;
    private String merchandise;
    private String userSign;
    private String status;
    private String amount;
    private String currency;
    private String discount;

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getWeChatOrderSn() {
        return weChatOrderSn;
    }

    public void setWeChatOrderSn(String weChatOrderSn) {
        this.weChatOrderSn = weChatOrderSn;
    }

    public String getMchOrderSn() {
        return mchOrderSn;
    }

    public void setMchOrderSn(String mchOrderSn) {
        this.mchOrderSn = mchOrderSn;
    }

    public String getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("交易时间", "tradeDate");
        tableHeader.put("微信支付单号", "weChatOrderSn");
        tableHeader.put("商户订单号", "mchOrderSn");
        tableHeader.put("内部订单号", "merchandise");
        tableHeader.put("用户标识", "userSign");
        tableHeader.put("交易状态", "status");
        tableHeader.put("交易金额", "amount");
        tableHeader.put("货币类型", "currency");
        tableHeader.put("现金券抵扣金额(元)", "discount");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<WeChatPayTableDto> weChatPayTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(WeChatPayTableDto weChatPayTableDto: weChatPayTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("tradeDate", weChatPayTableDto.getTradeDate());
            rowData.put("weChatOrderSn", weChatPayTableDto.getWeChatOrderSn());
            rowData.put("mchOrderSn", weChatPayTableDto.getMchOrderSn());
            rowData.put("merchandise", weChatPayTableDto.getMerchandise());
            rowData.put("userSign", weChatPayTableDto.getUserSign());
            rowData.put("status", weChatPayTableDto.getStatus());
            rowData.put("amount", weChatPayTableDto.getAmount());
            rowData.put("currency", weChatPayTableDto.getCurrency());
            rowData.put("discount", weChatPayTableDto.getDiscount());
            tableData.add(rowData);
        }
        return tableData;
    }
}
