package com.iuni.data.iplib.iuni;

/**
 * Country实体类
 * 包括ID(例：CN，AU，UK)，英文名，中文名
 * @author Nicholas
 */
public class Country {
    private String countryId;
    private String engName;
    private String chsName;

    public Country(String countryId, String engName, String chsName) {
        this.countryId = countryId;
        this.engName = engName;
        this.chsName = chsName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getChsName() {
        return chsName;
    }

    public void setChsName(String chsName) {
        this.chsName = chsName;
    }

    @Override
    public String toString() {
        return "Country [countryId=" + countryId + ", engName=" + engName + ", chsName=" + chsName + "]";
    }
}
