package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * 用户行为查询结果
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class UserBehaviorTableDto extends AbstractTableDto {

    /**
     * 注册时间
     */
    private String registerDateRange;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String phone;
    /**
     * 总登录次数
     */
    private long loginNum;
    /**
     * 1日前登录次数
     */
    private long loginIn1DayAgo;
    /**
     * 2－3日前登录次数
     */
    private long loginIn2To3DaysAgo;
    /**
     * 4－7日前登录次数
     */
    private long loginIn4To7DaysAgo;
    /**
     * 8－30日前登录次数
     */
    private long loginIn8To30DaysAgo;
    /**
     * 31－90日前登录次数
     */
    private long loginIn31To90DaysAgo;
    /**
     * 91在前登录次数
     */
    private long loginIn91DaysAgo;

    public String getRegisterDateRange() {
        return registerDateRange;
    }

    public void setRegisterDateRange(String registerDateRange) {
        this.registerDateRange = registerDateRange;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(long loginNum) {
        this.loginNum = loginNum;
    }

    public long getLoginIn1DayAgo() {
        return loginIn1DayAgo;
    }

    public void setLoginIn1DayAgo(long loginIn1DayAgo) {
        this.loginIn1DayAgo = loginIn1DayAgo;
    }

    public long getLoginIn2To3DaysAgo() {
        return loginIn2To3DaysAgo;
    }

    public void setLoginIn2To3DaysAgo(long loginIn2To3DaysAgo) {
        this.loginIn2To3DaysAgo = loginIn2To3DaysAgo;
    }

    public long getLoginIn4To7DaysAgo() {
        return loginIn4To7DaysAgo;
    }

    public void setLoginIn4To7DaysAgo(long loginIn4To7DaysAgo) {
        this.loginIn4To7DaysAgo = loginIn4To7DaysAgo;
    }

    public long getLoginIn8To30DaysAgo() {
        return loginIn8To30DaysAgo;
    }

    public void setLoginIn8To30DaysAgo(long loginIn8To30DaysAgo) {
        this.loginIn8To30DaysAgo = loginIn8To30DaysAgo;
    }

    public long getLoginIn31To90DaysAgo() {
        return loginIn31To90DaysAgo;
    }

    public void setLoginIn31To90DaysAgo(long loginIn31To90DaysAgo) {
        this.loginIn31To90DaysAgo = loginIn31To90DaysAgo;
    }

    public long getLoginIn91DaysAgo() {
        return loginIn91DaysAgo;
    }

    public void setLoginIn91DaysAgo(long loginIn91DaysAgo) {
        this.loginIn91DaysAgo = loginIn91DaysAgo;
    }

    /**
     * 生成表头
     *
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("注册时间", "registerDateRange");
        tableHeader.put("用户id", "userId");
        tableHeader.put("用户名", "userName");
        tableHeader.put("电子邮箱", "email");
        tableHeader.put("手机", "phone");
        tableHeader.put("总登录次数", "loginNum");
        tableHeader.put("1日前登录次数", "loginIn1DayAgo");
        tableHeader.put("2－3日前登录次数", "loginIn2To3DaysAgo");
        tableHeader.put("4－7日前登录次数", "loginIn4To7DaysAgo");
        tableHeader.put("8－30日前登录次数", "loginIn8To30DaysAgo");
        tableHeader.put("31－90日前登录次数", "loginIn31To90DaysAgo");
        tableHeader.put("91日前登录次数", "loginIn91DaysAgo");
        return tableHeader;
    }

    /**
     * 生成表数据
     *
     * @param userBehaviorTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<UserBehaviorTableDto> userBehaviorTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (UserBehaviorTableDto userBehaviorTableDto : userBehaviorTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("registerDateRange", userBehaviorTableDto.getRegisterDateRange());
            rowData.put("userId", userBehaviorTableDto.getUserId());
            rowData.put("userName", userBehaviorTableDto.getUserName());
            rowData.put("email", userBehaviorTableDto.getEmail());
            rowData.put("phone", userBehaviorTableDto.getPhone());
            rowData.put("loginNum", userBehaviorTableDto.getLoginNum());
            rowData.put("loginIn1DayAgo", userBehaviorTableDto.getLoginIn1DayAgo());
            rowData.put("loginIn2To3DaysAgo", userBehaviorTableDto.getLoginIn2To3DaysAgo());
            rowData.put("loginIn4To7DaysAgo", userBehaviorTableDto.getLoginIn4To7DaysAgo());
            rowData.put("loginIn8To30DaysAgo", userBehaviorTableDto.getLoginIn8To30DaysAgo());
            rowData.put("loginIn31To90DaysAgo", userBehaviorTableDto.getLoginIn31To90DaysAgo());
            rowData.put("loginIn91DaysAgo", userBehaviorTableDto.getLoginIn91DaysAgo());
            tableData.add(rowData);
        }
        return tableData;
    }

}
