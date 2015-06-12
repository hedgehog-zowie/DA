package com.iuni.data.conf.iplib;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum IpLibType {
    OTHER(null),
    IUNI("com.iuni.data.iplib.IuniIpLib"),
    TAOBAO("com.iuni.data.iplib.TaobaoIpLib"),
    PURE("com.iuni.data.iplib.PureIpLib"),
    GEO("com.iuni.data.iplib.GeoIpLib"),;

    private final String ipLibClassName;

    private IpLibType(String ipLibClassName) {
        this.ipLibClassName = ipLibClassName;
    }

    public String getIpLibClassName() {
        return ipLibClassName;
    }

}
