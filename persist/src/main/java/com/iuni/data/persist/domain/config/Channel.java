package com.iuni.data.persist.domain.config;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;

import javax.persistence.*;
import java.util.*;

/**
 * The persistent class for the IUNI_DA_CHANNEL database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_CHANNEL")
public class Channel extends AbstractConfig {

    @Column(name = "\"NAME\"")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Transient
    public static int CODE_LENGTH = 10;

    @Column(name = "ORIGINAL_URL")
    private String originalUrl;

    @Transient
    private String channelSerial;

    @Transient
    private String activeDate;

    @Column(name = "PROMOTION_URL")
    private String promotionUrl;

    @Column(name = "SHORT_URL")
    private String shortUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TYPE_ID")
    private ChannelType channelType;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<PageWebKpi> pageWebKpiSet;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<ClickWebKpi> clickWebKpiSet;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<WebKpi> webKpiSet;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getChannelSerial() {
        return channelSerial;
    }

    public void setChannelSerial(String channelSerial) {
        this.channelSerial = channelSerial;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getPromotionUrl() {
        return promotionUrl;
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Set<PageWebKpi> getPageWebKpiSet() {
        return pageWebKpiSet;
    }

    public void setPageWebKpiSet(Set<PageWebKpi> pageWebKpiSet) {
        this.pageWebKpiSet = pageWebKpiSet;
    }

    public Set<ClickWebKpi> getClickWebKpiSet() {
        return clickWebKpiSet;
    }

    public void setClickWebKpiSet(Set<ClickWebKpi> clickWebKpiSet) {
        this.clickWebKpiSet = clickWebKpiSet;
    }

    public Set<WebKpi> getWebKpiSet() {
        return webKpiSet;
    }

    public void setWebKpiSet(Set<WebKpi> webKpiSet) {
        this.webKpiSet = webKpiSet;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", channelSerial='" + channelSerial + '\'' +
                ", activeDate='" + activeDate + '\'' +
                ", promotionUrl='" + promotionUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("名称", "name");
        tableHeader.put("渠道AD编码", "code");
        tableHeader.put("渠道类型", "channelType");
        tableHeader.put("原始链接", "originalUrl");
        tableHeader.put("推广链接", "promotionUrl");
        tableHeader.put("短链接", "shortUrl");
        tableHeader.put("备注", "desc");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<Channel> channelList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (Channel channel : channelList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("name", channel.getName());
            rowData.put("code", channel.getCode());
            rowData.put("channelType", channel.getChannelType());
            rowData.put("originalUrl", channel.getOriginalUrl());
            rowData.put("promotionUrl", channel.getPromotionUrl());
            rowData.put("shortUrl", channel.getShortUrl());
            rowData.put("desc", channel.getDesc());
            tableData.add(rowData);
        }
        return tableData;
    }
}
