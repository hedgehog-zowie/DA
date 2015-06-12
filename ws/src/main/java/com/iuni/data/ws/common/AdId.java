package com.iuni.data.ws.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum AdId {
    os_1("1", "内部", "IUNI公司邮件"),
    os_2("2", "金立", "IUNIOS微博+iuni转发"),
    os_3("3", "外部", "微博大号"),
    os_4("4", "金立:易拉宝", "IUNI百度贴吧"),
    os_5("5", "金立:易拉宝", "IUNI手机微信"),
    os_6("6", "金立:易拉宝", "IUNI OS 论坛"),
    os_7("7", "丰盛町", "微信大号"),
    os_8("8", "公司", "公司易拉宝"),
    os_9("9", "食堂", "IUNIOS 微信"),
    ;

    private String id;
    private String name;
    private String way;

    AdId(String id, String name, String way) {
        this.id = id;
        this.name = name;
        this.way = way;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWay() {
        return way;
    }

    public AdId getAdId(String sys, int id){
        return AdId.valueOf(sys + "_" + id);
    }
}
