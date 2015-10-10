package com.iuni.data.persist.model.activity;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.model.AbstractQueryDto;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ChannelQueryDto extends AbstractQueryDto {

    private Channel channel;

    private String dataType;

    public ChannelQueryDto() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public enum DataType{
        pv("pv","pv"),
        uv("uv","uv"),
        vv("vv","vv"),
        on("on","下单总数量"),
        oa("on","下单总金额"),
        pn("pn","已支付订单总数量"),
        pa("pn","已支付订单总金额"),
        ;

        private final String code;
        private final String name;

        DataType(String code, String name){
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }
}
