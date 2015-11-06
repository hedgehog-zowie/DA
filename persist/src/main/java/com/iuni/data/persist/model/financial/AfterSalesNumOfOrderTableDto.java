package com.iuni.data.persist.model.financial;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class AfterSalesNumOfOrderTableDto {

    private String orderSn;
    private Date addTime;
    private String userName;
    private String mobile;
    private String type;
    private String sn;
    private Date createTime;
    private String status;
    private String causeInfo;
    private String remark;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCauseInfo() {
        return causeInfo;
    }

    public void setCauseInfo(String causeInfo) {
        this.causeInfo = causeInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单号", "orderSn");
        tableHeader.put("下单时间", "addTime");
        tableHeader.put("收件人姓名", "userName");
        tableHeader.put("收货人手机", "mobile");
        tableHeader.put("售后类型", "type");
        tableHeader.put("换货/维修单号", "sn");
        tableHeader.put("退/换/修申请时间", "createTime");
        tableHeader.put("售后单当前状态", "status");
        tableHeader.put("申请原因", "causeInfo");
        tableHeader.put("售后审核结果", "remark");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<AfterSalesNumOfOrderTableDto> afterSalesNumOfOrderTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(AfterSalesNumOfOrderTableDto afterSalesNumOfOrderTableDto : afterSalesNumOfOrderTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderSn", afterSalesNumOfOrderTableDto.getOrderSn());
            rowData.put("addTime", afterSalesNumOfOrderTableDto.getAddTime());
            rowData.put("userName", afterSalesNumOfOrderTableDto.getUserName());
            rowData.put("mobile", afterSalesNumOfOrderTableDto.getMobile());
            rowData.put("type", afterSalesNumOfOrderTableDto.getType());
            rowData.put("sn", afterSalesNumOfOrderTableDto.getSn());
            rowData.put("createTime", afterSalesNumOfOrderTableDto.getCreateTime());
            rowData.put("status", afterSalesNumOfOrderTableDto.getStatus());
            rowData.put("causeInfo", afterSalesNumOfOrderTableDto.getCauseInfo());
            rowData.put("remark", afterSalesNumOfOrderTableDto.getRemark());
            tableData.add(rowData);
        }
        return tableData;
    }
}
