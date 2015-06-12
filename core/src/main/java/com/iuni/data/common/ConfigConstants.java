package com.iuni.data.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ConfigConstants {

    public static final String ENV_REPORT_HOME = "REPORT_HOME";

    public static final Integer LOGICAL_CANCEL_FLAG_CANCEL = 1;
    public static final Integer LOGICAL_CANCEL_FLAG_NOT_CANCEL = 0;

    public static final Integer STATUS_FLAG_EFFECTIVE = 1;
    public static final Integer STATUS_FLAG_INVALID = 0;

    public static final Integer RTAG_TYPE_SYSTEM = 1;
    public static final Integer RTAG_TYPE_MODULE = 2;
    public static final Integer RTAG_TYPE_PAGE = 3;
    public static final Integer RTAG_TYPE_ACTION = 4;

    public static final Integer CHAIN_STEP_TYPE_PAGE = 1;
    public static final Integer CHAIN_STEP_TYPE_ACTION = 2;
    public static final Integer CHAIN_STEP_TYPE_DATA = 3;

}
