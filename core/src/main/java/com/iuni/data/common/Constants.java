package com.iuni.data.common;

public interface Constants {

    // time type begin
    public static final String TTYPE_SECOND = "ss";
    public static final String TTYPE_MINUTE = "mm";
    public static final String TTYPE_HOUR = "HH";
    public static final String TTYPE_DAY = "dd";
    public static final String TTYPE_MONTH = "MM";
    public static final String TTYPE_YEAR = "yyyy";
    // time type end

    // hive config begin
    public static final String hiveDriver = "hive.driver";
    public static final String hiveUrl = "hive.url";
    public static final String hiveUser = "hive.user";
    public static final String HIVE_USER_DEFAULT = "hive";
    public static final String hivePassword = "hive.password";
    public static final String hiveCurrentTableName = "hive.dataTable.name.current";
    public static final String hiveHistoryTableName = "hive.dataTable.name.history";
    public static final String HIVE_TABLE_NAME_CURRENT = "iunilog";
    public static final String HIVE_TABLE_NAME_HISTORY = "iuniloghistory";
    // hive config end

    // impala config begin
    public static final String impalaDriver = "impala.driver";
    public static final String impalaUrl = "impala.url";
    // impala config end

    // hbase config begin
    public static final String hbaseQuorum = "hbase.zookeeper.quorum";
    public static final int default_batch_size = 50;
    public static final int default_catch_size = 10000;
    public static final boolean default_catch_blocks = false;
    // hbase config end

    // hbase table config begin
    // 页面上报表名配置
    public static final String pageReportDataTable = "page.dataTable.name";
    public static final String pageReportDataTableDefault = "prd";
    // 页面上报列族配置
    public static final String pageReportDataTableCf = "page.dataTable.cf.name";
    public static final String pageReportDataTableCfDefault = "f";
    // 页面上报列配置
    public static final String pageReportDataTableColumn = "page.dataTable.column.name";
    public static final String pageReportDataTableColumnDefault = "s";
    // UV表
    public static final String hbaseTable_UV = "uv.dataTable.name";
    public static final String hbaseTable_UV_DEFAULT = "uv";
    // VV表
    public static final String hbaseTable_VV = "vv.dataTable.name";
    public static final String hbaseTable_VV_DEFAULT = "vv";
    // IP表
    public static final String hbaseTable_IP = "ip.dataTable.name";
    public static final String hbaseTable_IP_DEFAULT = "ip";
    // 计数器-总-列名
    public static final String hbaseTable_qualifierTotal = "total";
    // hbase table config end

    public static final String DEFAULT_CHANNEL_CODE = "000000";
    public static final String DEFAULT_RTAG_CODE = "000000";

    // manual config
    public static final String manual = "manual";
    public static final boolean MANUAL_DEFAULT = false;
    public static final String manualStart = "manual.start";
    public static final String manualEnd = "manual.end";
    // cron config
    public static final String cron = "cron";
    public static final String thread = "thread";
    public static final int THREAD_NUM_DEFAULT = 10;
    // time type
    public static final String timeType = "time.type";
    public static final String TIME_TYPE_DEFAULT = "day";
    // setBasicInfoForCreate partition flag
    public static final String createPartition = "createPartition";
    public static final boolean CREATE_PARTITION_DEFAULT = true;

    public static final String default_flow_source_name = "其它";
    public static final String default_country = "中国";

}
