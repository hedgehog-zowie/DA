package com.iuni.data.persist.model.financial;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class AliPayTableDto extends AbstractTableDto {

    private String aliPayOrderNo;
    private String merchantOrderNo;
    private String createTime;
    private String oppositeName;
    private String osn;
    private String orderCode;
    private String inAmount;
    private String outAmount;
    private String balance;
    private String type;
    private String memo;

    public String getAliPayOrderNo() {
        return aliPayOrderNo;
    }

    public void setAliPayOrderNo(String aliPayOrderNo) {
        this.aliPayOrderNo = aliPayOrderNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOppositeName() {
        return oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getOsn() {
        return osn;
    }

    public void setOsn(String osn) {
        this.osn = osn;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getInAmount() {
        return inAmount;
    }

    public void setInAmount(String inAmount) {
        this.inAmount = inAmount;
    }

    public String getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("业务流水号", "aliPayOrderNo");
        tableHeader.put("商户订单号", "merchantOrderNo");
        tableHeader.put("发生时间", "createTime");
        tableHeader.put("对方账号", "oppositeName");
        tableHeader.put("天猫外部订单号", "osn");
        tableHeader.put("内部订单号", "orderCode");
        tableHeader.put("收入金额（+元）", "inAmount");
        tableHeader.put("支出金额（-元）", "outAmount");
        tableHeader.put("账户余额（元）", "balance");
        tableHeader.put("业务类型", "type");
        tableHeader.put("备注", "memo");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<AliPayTableDto> aliPayTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(AliPayTableDto aliPayTableDto: aliPayTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("aliPayOrderNo", aliPayTableDto.getAliPayOrderNo());
            rowData.put("merchantOrderNo", aliPayTableDto.getMerchantOrderNo());
            rowData.put("createTime", aliPayTableDto.getCreateTime());
            rowData.put("oppositeName", aliPayTableDto.getOppositeName());
            rowData.put("osn", aliPayTableDto.getOsn());
            rowData.put("orderCode", aliPayTableDto.getOrderCode());
            rowData.put("inAmount", aliPayTableDto.getInAmount());
            rowData.put("outAmount", aliPayTableDto.getOutAmount());
            rowData.put("balance", aliPayTableDto.getBalance());
            rowData.put("type", aliPayTableDto.getType());
            rowData.put("memo", aliPayTableDto.getMemo());
            tableData.add(rowData);
        }
        return tableData;
    }

}
