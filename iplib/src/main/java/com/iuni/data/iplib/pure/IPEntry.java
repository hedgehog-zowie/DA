package com.iuni.data.iplib.pure;

/**
 * 一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP
 * @author CaiKe
 * @version dp-service-V1.0.2
 */
public class IPEntry {
	public String beginIp;
	public String endIp;
	public String area;
	public String location;

	public IPEntry() {
		beginIp = endIp = area = location = "";
	}

	public String toString() {
		return this.area + " " + this.location + "的IP范围: " + this.beginIp + " - "
				+ this.endIp;
	}
}