package com.iuni.data.persist.domain.config;

import javax.persistence.*;

/**
 * The persistent class for the IUNI_DA_CHAIN_STEP database table.
 */
@Entity
@Table(name = "IUNI_DA_CHAIN_STEP")
public class ChainStep extends AbstractConfig {

    @Column(name="\"NAME\"")
    private String name;

    //bi-directional many-to-one association to Chain
    @ManyToOne
    @JoinColumn(name = "CHAIN_ID")
    private Chain chain;

    //bi-directional many-to-one association to ChainStepType
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private ChainStepType chainStepType;

    @Column(name = "STEP_INDEX")
    private Integer stepIndex;

    @Column(name = "PAGE_NAME")
    private String pageName;

    @Column(name = "PAGE_URL")
    private String pageUrl;

    //bi-directional many-to-one association to RTag
    @ManyToOne
    @JoinColumn(name = "RTAG_ID")
    private RTag rTag;

    @Column(name = "ORDER_TYPE")
    private String orderType;

    public ChainStep() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chain getChain() {
        return this.chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public ChainStepType getChainStepType() {
        return chainStepType;
    }

    public void setChainStepType(ChainStepType chainStepType) {
        this.chainStepType = chainStepType;
    }

    public RTag getrTag() {
        return rTag;
    }

    public void setrTag(RTag rTag) {
        this.rTag = rTag;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}