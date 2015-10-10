package com.iuni.data.ws.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum CookieKey {
    VK("vk"),
    SID("sid"),
    UID("uid"),
    ADID("ad_id");

    private String key;
    private String value;

    CookieKey(String key) {
        this.key = key;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String getName() {
        return this.key;
    }
}
