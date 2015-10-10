package com.iuni.data.persist.domain.config;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private String channelType;

    @Transient
    private String channelSerial;

    @Transient
    private String activeDate;

    @Column(name = "PROMOTION_URL")
    private String promotionUrl;

    @Column(name = "SHORT_URL")
    private String shortUrl;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<PageWebKpi> pageWebKpis;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<ClickWebKpi> clickWebKpis;

    @OneToMany(mappedBy = "channel", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<WebKpi> webKpis;

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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
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

    public Set<PageWebKpi> getPageWebKpis() {
        return pageWebKpis;
    }

    public void setPageWebKpis(Set<PageWebKpi> pageWebKpis) {
        this.pageWebKpis = pageWebKpis;
    }

    public Set<ClickWebKpi> getClickWebKpis() {
        return clickWebKpis;
    }

    public void setClickWebKpis(Set<ClickWebKpi> clickWebKpis) {
        this.clickWebKpis = clickWebKpis;
    }

    public Set<WebKpi> getWebKpis() {
        return webKpis;
    }

    public void setWebKpis(Set<WebKpi> webKpis) {
        this.webKpis = webKpis;
    }


    @Override
    public String toString() {
        return "Channel{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", channelType=" + channelType +
                ", channelSerial=" + channelSerial +
                ", activeDate=" + activeDate +
                ", promotionUrl='" + promotionUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

//    public Map<String, String> toMap(){
//        Map<String, String> map = new HashMap<>();
//        map.put("code", code);
//        map.put("channelType", channelType);
//        map.put("channelSerial", channelSerial);
//        map.put("activeDate", activeDate);
//        map.put("name", name);
//        map.put("originalUrl", originalUrl);
//        map.put("promotionUrl", promotionUrl);
//        map.put("shortUrl", shortUrl);
//        map.put("desc", desc);
//        return map;
//    }

    public enum ChannelType {
        EDM(30, "30-EMD"),
        EDM_EMAIL(31, "31-MED-邮件"),
        EDM_MSG(32, "32-MED-短信"),
        OSITE(40, "40-站外推广"),
        TXGDT(41, "41-腾讯广点通"),
        JXDSP(42, "42-聚效DSP"),
        BDSEM(43, "43-百度sem"),
        SMZDM(44, "44-什么值得买"),
        QFQ(45, "45-趣分期"),
        CZMT(46, "46-垂直媒体"),
        ACT(50, "50-活动"),
        ;

        private int id;
        private String name;

        ChannelType() {

        }

        ChannelType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        static public List<ChannelType> getAllChannelType() {
            List<ChannelType> channelTypeList = new ArrayList();
            channelTypeList.add(ChannelType.EDM);
            channelTypeList.add(ChannelType.EDM_EMAIL);
            channelTypeList.add(ChannelType.EDM_MSG);
            channelTypeList.add(ChannelType.OSITE);
            channelTypeList.add(ChannelType.TXGDT);
            channelTypeList.add(ChannelType.JXDSP);
            channelTypeList.add(ChannelType.BDSEM);
            channelTypeList.add(ChannelType.SMZDM);
            channelTypeList.add(ChannelType.QFQ);
            channelTypeList.add(ChannelType.CZMT);
            channelTypeList.add(ChannelType.ACT);
            return channelTypeList;
        }
    }
}
