package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum PageName {
    index("common/index"),

    config_channel("config/config-channel"),
    config_channel_edit("config/config-channel-edit"),
    config_channel_type("config/config-channel-type"),
    config_channel_type_edit("config/config-channel-type-edit"),

    config_buriedPoint("config/config-buried-point"),
    config_buriedPoint_edit("config/config-buried-point-edit"),

    flow_buried_point("flow/flow-buried-point"),
    flow_buried_point_today("flow/flow-buried-point-today"),

    active_channel("activity/activity-channel"),

    financial_transfer_details("financial/financial-transfer-details"),

    ;


    
    private final String path;

    PageName(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
