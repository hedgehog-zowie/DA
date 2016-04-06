package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * 用户注册结果
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class RegisterTableDto extends AbstractTableDto {

    /**
     * 日期
     */
    private String date;
    /**
     * 注册页PV
     */
    private long pv;
    /**
     * 注册页UV
     */
    private long uv;
    /**
     * 商城注册会员数
     */
    private long registerNum;
    /**
     * 注册成功率
     */
    private double registerRate;
    /**
     * 新浪微博联合登录
     */
    private long sinaLoginNum;
    /**
     * QQ联合登录
     */
    private long qqLoginNum;
    /**
     * 支付宝联合登录
     */
    private long aliLoginNum;
    /**
     * 豆瓣联合登录
     */
    private long doubanLoginNum;
    /**
     * 购买活跃用户数
     */
    private long boughtUserNum;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(long registerNum) {
        this.registerNum = registerNum;
    }

    public double getRegisterRate() {
        return registerRate;
    }

    public void setRegisterRate(double registerRate) {
        this.registerRate = registerRate;
    }

    public long getSinaLoginNum() {
        return sinaLoginNum;
    }

    public void setSinaLoginNum(long sinaLoginNum) {
        this.sinaLoginNum = sinaLoginNum;
    }

    public long getQqLoginNum() {
        return qqLoginNum;
    }

    public void setQqLoginNum(long qqLoginNum) {
        this.qqLoginNum = qqLoginNum;
    }

    public long getAliLoginNum() {
        return aliLoginNum;
    }

    public void setAliLoginNum(long aliLoginNum) {
        this.aliLoginNum = aliLoginNum;
    }

    public long getDoubanLoginNum() {
        return doubanLoginNum;
    }

    public void setDoubanLoginNum(long doubanLoginNum) {
        this.doubanLoginNum = doubanLoginNum;
    }

    public long getBoughtUserNum() {
        return boughtUserNum;
    }

    public void setBoughtUserNum(long boughtUserNum) {
        this.boughtUserNum = boughtUserNum;
    }

    /**
     * 生成表头
     *
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "date");
        tableHeader.put("注册页PV", "pv");
        tableHeader.put("注册页UV", "uv");
        tableHeader.put("商城注册会员数", "registerNum");
        tableHeader.put("注册成功率", "registerRate");
        tableHeader.put("新浪微博联合登录", "sinaLoginNum");
        tableHeader.put("QQ联合登录", "qqLoginNum");
        tableHeader.put("支付宝联合登录", "aliLoginNum");
        tableHeader.put("豆瓣联合登录", "doubanLoginNum");
        tableHeader.put("购买活跃用户数", "boughtUserNum");
        return tableHeader;
    }

    /**
     * 生成表数据
     *
     * @param registerTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<RegisterTableDto> registerTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (RegisterTableDto registerTableDto : registerTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("date", registerTableDto.getDate());
            rowData.put("pv", registerTableDto.getPv());
            rowData.put("uv", registerTableDto.getUv());
            rowData.put("registerNum", registerTableDto.getRegisterNum());
            rowData.put("registerRate", registerTableDto.getRegisterRate());
            rowData.put("sinaLoginNum", registerTableDto.getSinaLoginNum());
            rowData.put("qqLoginNum", registerTableDto.getQqLoginNum());
            rowData.put("aliLoginNum", registerTableDto.getAliLoginNum());
            rowData.put("doubanLoginNum", registerTableDto.getDoubanLoginNum());
            rowData.put("boughtUserNum", registerTableDto.getBoughtUserNum());
            tableData.add(rowData);
        }
        return tableData;
    }

}
