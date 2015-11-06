package com.iuni.data.persist.model.system;

/**
 * 订单来源
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class OrderSourceDto {
    /**
     * 订单来源编码
     */
    private String sourceCode;
    /**
     * 订单来源名称
     */
    private String sourceName;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

}
