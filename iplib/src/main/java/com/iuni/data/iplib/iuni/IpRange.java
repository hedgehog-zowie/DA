package com.iuni.data.iplib.iuni;

/**
 * IpRange
 * Created by Administrator on 2014/6/28 0028.
 */
public class IpRange {
    private String startIpStr;
    private byte[] startIp;
    private int hostNum;
    private String countryId;

    public IpRange(String startIpStr, byte[] startIp, int hostNum, String countryId) {
        this.startIpStr = startIpStr;
        this.startIp = startIp;
        this.hostNum = hostNum;
        this.countryId = countryId;
    }

    public byte[] getStartIp() {
        return startIp;
    }

    public long getHostNum() {
        return hostNum;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getStartIpStr() {
        return startIpStr;
    }

    @Override
    public String toString() {
        return "IpRange [startIpStr=" + startIpStr + ", hostNum=" + hostNum
                + ", countryId=" + countryId + "]";
    }
}
