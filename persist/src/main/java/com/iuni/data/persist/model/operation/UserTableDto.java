package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * 用户查询结果
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
public class UserTableDto extends AbstractTableDto {

    /**
     * 注册时间
     */
    private String registerDate;
    /**
     * 注册用户数
     */
    private long registerNum;
    /**
     * 1日留存数
     */
    private long one_day_stay;
    /**
     * 2-3日留存数
     */
    private long two_three_days_stay;
    /**
     * 4-7日留存数
     */
    private long four_seven_days_stay;
    /**
     * 8-30日留存数
     */
    private long eight_thirty_days_stay;
    /**
     * 31-90日留存数
     */
    private long thirtyOne_ninety_days_stay;

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public long getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(long registerNum) {
        this.registerNum = registerNum;
    }

    public long getOne_day_stay() {
        return one_day_stay;
    }

    public void setOne_day_stay(long one_day_stay) {
        this.one_day_stay = one_day_stay;
    }

    public long getTwo_three_days_stay() {
        return two_three_days_stay;
    }

    public void setTwo_three_days_stay(long two_three_days_stay) {
        this.two_three_days_stay = two_three_days_stay;
    }

    public long getFour_seven_days_stay() {
        return four_seven_days_stay;
    }

    public void setFour_seven_days_stay(long four_seven_days_stay) {
        this.four_seven_days_stay = four_seven_days_stay;
    }

    public long getEight_thirty_days_stay() {
        return eight_thirty_days_stay;
    }

    public void setEight_thirty_days_stay(long eight_thirty_days_stay) {
        this.eight_thirty_days_stay = eight_thirty_days_stay;
    }

    public long getThirtyOne_ninety_days_stay() {
        return thirtyOne_ninety_days_stay;
    }

    public void setThirtyOne_ninety_days_stay(long thirtyOne_ninety_days_stay) {
        this.thirtyOne_ninety_days_stay = thirtyOne_ninety_days_stay;
    }

    /**
     * 生成表头
     *
     * @return
     */
    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("注册时间", "registerDate");
        tableHeader.put("注册用户数", "registerNum");
        tableHeader.put("1日留存数", "one_day_stay");
        tableHeader.put("2-3日留存数", "two_three_days_stay");
        tableHeader.put("4-7日留存数", "four_seven_days_stay");
        tableHeader.put("8-30日留存数", "eight_thirty_days_stay");
        tableHeader.put("31-90日留存数", "thirtyOne_ninety_days_stay");
        return tableHeader;
    }

    /**
     * 生成表数据
     *
     * @param userTableDtoList
     * @return
     */
    public static List<Map<String, Object>> generateTableData(List<UserTableDto> userTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (UserTableDto userTableDto : userTableDtoList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("registerDate", userTableDto.getRegisterDate());
            rowData.put("registerNum", userTableDto.getRegisterNum());
            rowData.put("one_day_stay", userTableDto.getOne_day_stay());
            rowData.put("two_three_days_stay", userTableDto.getTwo_three_days_stay());
            rowData.put("four_seven_days_stay", userTableDto.getFour_seven_days_stay());
            rowData.put("eight_thirty_days_stay", userTableDto.getEight_thirty_days_stay());
            rowData.put("thirtyOne_ninety_days_stay", userTableDto.getThirtyOne_ninety_days_stay());
            tableData.add(rowData);
        }
        return tableData;
    }

}
