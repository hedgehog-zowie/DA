package com.iuni.data.persist.model.operation;

import com.iuni.data.persist.model.AbstractQueryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SalesQueryDto extends AbstractQueryDto {

    protected static final String splitterOfOrderSource = ",";
    protected static final String splitterOfSku = ",";

    /**
     * 统计方式
     */
    private String dateStyle;
    /**
     * 订单来源，页面参数
     */
    private String orderSourceStr;
    /**
     * 订单来源，数据库查询参数
     */
    private String[] orderSources;
    /**
     * 商品类型，需要列出全部手机及除手机外的其他商品种类（如：配件）
     */
    private String type;
    /**
     * 规格型号，若商品类型为手机，列出该手机的全部型号，若商品类型为种类（如：配件），则需要列出该种类下的全部商品
     */
    private String model;
    /**
     * SKU编码，页面参数，多个SKU以逗号或分号作为分隔符
     */
    private String skuStr;
    /**
     * SKU编码，数据库查询参数
     */
    private String[] skus;

    public String getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    public String getOrderSourceStr() {
        return orderSourceStr;
    }

    public void setOrderSourceStr(String orderSourceStr) {
        this.orderSourceStr = orderSourceStr;
    }

    public String[] getOrderSources() {
        return orderSources;
    }

    public void setOrderSources(String[] orderSources) {
        this.orderSources = orderSources;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSkuStr() {
        return skuStr;
    }

    public void setSkuStr(String skuStr) {
        this.skuStr = skuStr;
    }

    public String[] getSkus() {
        return skus;
    }

    public void setSkus(String[] skus) {
        this.skus = skus;
    }

    /**
     * 解析订单来源
     */
    public void parseOrderSource() {
        if (StringUtils.isEmpty(orderSourceStr))
            return;
        String[] tmpSources = orderSourceStr.split(splitterOfOrderSource);
        List<String> tmpSourceList = Arrays.asList(tmpSources);
        for (String source : tmpSourceList)
            if (StringUtils.isEmpty(source.trim()))
                tmpSourceList.remove(source);
        orderSources = new String[tmpSourceList.size()];
        for (int i = 0; i < orderSources.length; i++)
            orderSources[i] = tmpSourceList.get(i).trim();
    }

    /**
     * 解析SKU编码
     */
    public void parseSku() {
        if (StringUtils.isEmpty(skuStr))
            return;
        String[] tmpSkus = skuStr.split(splitterOfSku);
        List<String> tmpSkuList = Arrays.asList(tmpSkus);
        for (String sku : tmpSkuList)
            if (StringUtils.isEmpty(sku.trim()))
                tmpSkuList.remove(sku);
        skus = new String[tmpSkuList.size()];
        for (int i = 0; i < skus.length; i++)
            skus[i] = tmpSkuList.get(i).trim();
    }

}
