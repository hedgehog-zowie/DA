package com.iuni.data.webapp.chart;

import java.util.EnumSet;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class AreaData {
    private String name;
    private Integer value;

    public AreaData(String name, Integer value) {
        this.name = formatName(name);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    private String formatName(String name) {
        EnumSet<Province> set = EnumSet.allOf(Province.class);
        for (Province province : set) {
            if (name.contains(province.getName()))
                return province.getName();
        }
        return null;
    }

    enum Province {
        BEIJING("北京"),
        TIANJING("天津"),
        SHANGHAI("上海"),
        CHONGQING("重庆"),
        HEBEI("河北"),
        HENAN("河南"),
        YUNNAN("云南"),
        LIAONING("辽宁"),
        HEILONGJIANG("黑龙江"),
        HUNAN("湖南"),
        ANHUI("安徽"),
        SHANDONG("山东"),
        XINJIANG("新疆"),
        JIANGSU("江苏"),
        ZHEJIANG("浙江"),
        JIANGXI("江西"),
        HUBEI("湖北"),
        GUANGXI("广西"),
        GANSU("甘肃"),
        SHANXI("山西"),
        NEIMENGGU("内蒙古"),
        SHAN2XI("陕西"),
        JILIN("吉林"),
        FUJIAN("福建"),
        GUIZHOU("贵州"),
        GUANGDONG("广东"),
        QINGHAI("青海"),
        XIZANG("西藏"),
        SICHUAN("四川"),
        NINGXIA("宁夏"),
        HAINAN("海南"),
        TAIWAN("台湾"),
        XIANGGANG("香港"),
        AOMEN("澳门"),;
        private String name;

        Province(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
