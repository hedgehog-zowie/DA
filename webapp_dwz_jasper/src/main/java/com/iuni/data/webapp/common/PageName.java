package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum PageName {
    config_pagetag("config/config-pagetag"),
    config_pagetag_edit("config/config-pagetag-edit"),

    config_chain("config/config-chain"),
    config_chain_edit("config/config-chain-edit"),
    config_chain_step("config/config-chain-step"),
    config_chain_step_page("config/config-chain-step-page"),
    config_chain_step_page_edit("config/config-chain-step-page-edit"),
    config_chain_step_action("config/config-chain-step-action"),
    config_chain_step_action_edit("config/config-chain-step-action-edit"),
    config_chain_step_action_rtag("config/config-chain-step-action-rtag"),
    config_chain_step_data("config/config-chain-step-data"),
    config_chain_step_data_edit("config/config-chain-step-data-edit"),

    config_holiday("config/config-holiday"),
    config_holiday_edit("config/config-holiday-edit"),

    config_flowSource("config/config-flow-source"),
    config_flowSource_edit("config/config-flow-source-edit"),

    config_advertisement("config/config-advertisement"),
    config_advertisement_add("config/config-advertisement-add"),
    config_advertisement_update("config/config-advertisement-setBasicInfoForUpdate"),
    
    config_register("config/config-register-page"),
    config_registerPage_add("config/config-register-page-add"),
    config_registerPage_update("config/config-register-page-setBasicInfoForUpdate"),

    config_user_defined_report("config/config-user-defined-report"),
    config_user_defined_report_edit("config/config-user-defined-report-edit"),

    flow_overview("flow/flow-overview"),
    flow_trend("flow/flow-trend"),
    flow_source("flow/flow-source"),
    flow_area("flow/flow-area"),

    active_flow("activity/activity-flow"),
    ;


    
    private final String path;

    PageName(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
