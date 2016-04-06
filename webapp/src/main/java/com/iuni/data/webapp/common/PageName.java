package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum PageName {
    index("common/index"),

    config_channel("config/channel"),
    config_channel_edit("config/channel-edit"),
    config_channel_type("config/channel-type"),
    config_channel_type_edit("config/channel-type-edit"),

    config_buriedPoint("config/buried-point"),
    config_buriedPoint_edit("config/buried-point-edit"),
    config_buriedGroup("config/buried-group"),
    config_buriedGroup_edit("config/buried-group-edit"),

    flow_buried_point("flow/flow-buried-point"),
    flow_buried_point_today("flow/flow-buried-point-today"),

    active_channel("activity/activity-channel"),

    financial_transfer_details("financial/transfer-details"),
    financial_sales_order_details("financial/sales-order-details"),
    financial_stock_move_details("financial/stock-move-details"),
    financial_no_invoice_sales_details("financial/no-invoice-sales-details"),
    financial_in_transfer_details("financial/in-transfer-details"),
    financial_stock_details("financial/stock-details"),
    financial_pay_amount_check_details("financial/pay-amount-check-details"),
    financial_refund_details("financial/refund-details"),
    financial_procurement_details("financial/procurement-details"),
    financial_not_in_warranty_details("financial/not-in-warranty-details"),
    financial_aliPay("financial/aliPay"),
    financial_rebates_details("financial/rebates-details"),
    financial_after_sales_num_of_order("financial/after-sales-num-of-order"),
    financial_weChat_pay("financial/weChat-pay"),
    financial_big_order_details("financial/big-order-details"),

    distribution_stock_by_source("distribution/stock-by-source"),
    distribution_stock("distribution/stock"),
    distribution_stock_by_channel("distribution/stock-by-channel"),
    distribution_stock_move_details("distribution/stock-move-details"),
    distribution_return_goods_details("distribution/return-goods-details"),
    distribution_transfer_details("distribution/transfer-details"),
    distribution_positive("distribution/positive"),
    distribution_reverse_sign("distribution/reverse-sign"),
    distribution_reverse_sign_of_back("distribution/reverse-sign-of-back"),
    distribution_reverse_sign_of_exchange("distribution/reverse-sign-of-exchange"),
    distribution_reverse_sign_of_repair("distribution/reverse-sign-of-repair"),
    distribution_freight("distribution/freight"),

    operation_sales("operation/sales"),
    operation_mall_sales("operation/mall-sales"),
    operation_convert_rate_of_order("operation/convert-rate-of-order"),
    operation_register("operation/register"),
    operation_user_behavior("operation/user-behavior"),
    operation_user("operation/user"),
    ;


    
    private final String path;

    PageName(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
