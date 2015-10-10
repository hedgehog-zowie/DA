/*==============================================================*/
/* Table: IUNI_DA_FLOW_SOURCE_TYPE                              */
/*==============================================================*/
create table IUNI_DA_FLOW_SOURCE_TYPE
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(64)         not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_FLOW_SOURCE_TYPE check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLOW_SOURCE_TYPE check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_FLOW_SOURCE_TYPE primary name (ID)
);
comment on table IUNI_DA_FLOW_SOURCE_TYPE is '流量来源类型表';
comment on column IUNI_DA_FLOW_SOURCE_TYPE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_FLOW_SOURCE_TYPE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_FLOW_SOURCE                                   */
/*==============================================================*/
create table IUNI_DA_FLOW_SOURCE
(
  ID                   INT                  not null,
  TYPE_ID              INT                  not null,
  NAME                 VARCHAR2(128)        not null,
  URL                  VARCHAR2(4000)       not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_SOURCE check (STATUS in (0,1)),
  CANCEL_FLAG          INTEGER              default 0 not null
    constraint CKC_CANCEL_FLAG_SOURCE check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_FLOW_SOURCE primary name (ID)
);
comment on table IUNI_DA_FLOW_SOURCE is '流量来源配置表';
comment on column IUNI_DA_FLOW_SOURCE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_FLOW_SOURCE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
alter table IUNI_DA_FLOW_SOURCE add constraint FK_FLOW_SOURCE_TYPE_ID foreign name (TYPE_ID) references IUNI_DA_FLOW_SOURCE_TYPE (ID);

/*==============================================================*/
/* Table: IUNI_DA_HOLIDAY_TYPE                                  */
/*==============================================================*/
create table IUNI_DA_HOLIDAY_TYPE
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(64)         not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_HOLIDAY_TYPE_STATUS check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_HOLIDAY_FLAG check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_HOLIDAY_TYPE primary name (ID)
);
comment on table IUNI_DA_HOLIDAY_TYPE is '节假日类型表';
comment on column IUNI_DA_HOLIDAY_TYPE.NAME is '节假日类型名';
comment on column IUNI_DA_HOLIDAY_TYPE.STATUS is '有效标识，0无效，1有效。';
comment on column IUNI_DA_HOLIDAY_TYPE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_HOLIDAY                                       */
/*==============================================================*/
create table IUNI_DA_HOLIDAY
(
  ID                   INT                  not null,
  TYPE_ID              INT,
  NAME                 VARCHAR2(64)         not null,
  START_DATE           DATE                 not null,
  END_DATE             DATE                 not null,
  YEAR                 INT,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_HOLIDAY check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_HOLIDAY check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_HOLIDAY primary name (ID)
);
comment on table IUNI_DA_HOLIDAY is '节假日配置表';
comment on column IUNI_DA_HOLIDAY.NAME is '节假日名称';
comment on column IUNI_DA_HOLIDAY.START_DATE is '节假日开始时间';
comment on column IUNI_DA_HOLIDAY.END_DATE is '节假日结束时间';
comment on column IUNI_DA_HOLIDAY.YEAR is '节假日所属年份';
comment on column IUNI_DA_HOLIDAY.STATUS is '有效标识，0无效，1有效。';
comment on column IUNI_DA_HOLIDAY.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
alter table IUNI_DA_HOLIDAY add constraint FK_IUNI_DA_HOLIDAY_TYPE_ID foreign name (TYPE_ID) references IUNI_DA_HOLIDAY_TYPE (ID);

/*==============================================================*/
/* Table: IUNI_DA_REGISTER_PAGE                                 */
/*==============================================================*/
create table IUNI_DA_REGISTER_PAGE
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(64)         not null,
  URL                  VARCHAR2(4000)       not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_REGISTER_PAGE check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_REGISTER_PAGE check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_REGISTER_PAGE primary name (ID)
);
comment on table IUNI_DA_REGISTER_PAGE is '注册入口页配置';
comment on column IUNI_DA_REGISTER_PAGE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_REGISTER_PAGE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_AD                                            */
/*==============================================================*/
create table IUNI_DA_AD
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(128)        not null,
  TYPE                 VARCHAR2(128)        not null,
  CHANNEL              VARCHAR2(128)        not null,
  POSITION             VARCHAR2(128)        not null,
  URL                  VARCHAR2(4000)       not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_AD check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_AD check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE             not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE             not null,
  constraint PK_IUNI_DA_AD primary name (ID)
);
comment on table IUNI_DA_AD is '广告渠道配置';
comment on column IUNI_DA_AD.NAME is '推广渠道';
comment on column IUNI_DA_AD.TYPE is '合作形式';
comment on column IUNI_DA_AD.CHANNEL is '频道';
comment on column IUNI_DA_AD.POSITION is '广告位';
comment on column IUNI_DA_AD.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_AD.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_RTAG_TYPE                                     */
/*==============================================================*/
create table IUNI_DA_RTAG_TYPE
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(128)        not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_RTAG_TYPE check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_RTAG_TYPE check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_RTAG_TYPE primary name (ID)
);
comment on table IUNI_DA_RTAG_TYPE is 'RTAG类型表';
comment on column IUNI_DA_RTAG_TYPE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_RTAG_TYPE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_RTAG                                          */
/*==============================================================*/
create table IUNI_DA_RTAG
(
  ID                   INT                  not null,
  RTAG                 VARCHAR2(32)         not null,
  NAME                 VARCHAR2(128)        not null,
  INFO                 VARCHAR2(4000)       not null,
  PARENT               VARCHAR2(32),
  TYPE_ID              INT                  not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_RTAG check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_RTAG check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_RTAG primary name (ID)
);
comment on table IUNI_DA_RTAG is 'RTAG定义表';
comment on column IUNI_DA_RTAG.PARENT is '父RTAG';
comment on column IUNI_DA_RTAG.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_RTAG.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
alter table IUNI_DA_RTAG add constraint FK_RTAT_TYPE_ID foreign name (TYPE_ID) references IUNI_DA_RTAG_TYPE (ID);

/*==============================================================*/
/* Table: IUNI_DA_CHAIN                                         */
/*==============================================================*/
create table IUNI_DA_CHAIN
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(128)        not null,
  PRODUCT              VARCHAR2(128),
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_CHAIN check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_CHAIN check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR(128)         not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR(128)         not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_CHAIN primary name (ID)
);
comment on table IUNI_DA_CHAIN is '关键路径定义表';
comment on column IUNI_DA_CHAIN.STATUS is '有效标识，0无效，1有效。';
comment on column IUNI_DA_CHAIN.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_CHAIN_STEP_TYPE                               */
/*==============================================================*/
create table IUNI_DA_CHAIN_STEP_TYPE
(
  ID                   INT                  not null,
  NAME                 VARCHAR2(128)        not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_STEP_TYPE check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_STEP_STEP check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_CHAIN_STEP_TYPE primary name (ID)
);
comment on table IUNI_DA_CHAIN_STEP_TYPE is '关键路径步骤类型表';
comment on column IUNI_DA_CHAIN_STEP_TYPE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_CHAIN_STEP_TYPE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

/*==============================================================*/
/* Table: IUNI_DA_CHAIN_STEP                                    */
/*==============================================================*/
create table IUNI_DA_CHAIN_STEP
(
  ID                   INT                  not null,
  CHAIN_ID             INT,
  TYPE_ID              INT,
  NAME                 VARCHAR2(128)        not null,
  STEP_INDEX           INT                  not null,
  PAGE_NAME            VARCHAR2(128),
  PAGE_URL             VARCHAR2(4000),
  RTAG_ID              INT,
  ORDER_TYPE           VARCHAR2(32),
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  default 1 not null
    constraint CKC_STATUS_CHAIN_STEP check (STATUS in (0,1)),
  CANCEL_FLAG          INT                  default 0 not null
    constraint CKC_CANCEL_FLAG_CHAIN_STEP check (CANCEL_FLAG in (0,1)),
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_CHAIN_STEP primary name (ID)
);
comment on table IUNI_DA_CHAIN_STEP is '关键路径步骤表';
comment on column IUNI_DA_CHAIN_STEP.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_CHAIN_STEP.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
alter table IUNI_DA_CHAIN_STEP add constraint FK_CHAIN_STEP_RTAG_ID foreign name (RTAG_ID) references IUNI_DA_RTAG (ID);
alter table IUNI_DA_CHAIN_STEP add constraint FK_CHAIN_STEP_CHAIN_ID foreign name (CHAIN_ID) references IUNI_DA_CHAIN (ID);
alter table IUNI_DA_CHAIN_STEP add constraint FK_CHAIN_STEP_TYPE_ID foreign name (TYPE_ID) references IUNI_DA_CHAIN_STEP_TYPE (ID);

/*==============================================================*/
/* Table: IUNI_DA_CHANNEL                                       */
/*==============================================================*/
create table IUNI_DA_CHANNEL
(
  ID                   INT                  not null,
  CODE                 VARCHAR2(16)         not null,
  NAME                 VARCHAR2(64)         not null,
  "DESC"               VARCHAR2(1024),
  STATUS               INT                  not null,
  CANCEL_FLAG          INT                  not null,
  CREATE_BY            VARCHAR2(128)        not null,
  CREATE_DATE          DATE                 not null,
  UPDATE_BY            VARCHAR2(128)        not null,
  UPDATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_CHANNEL primary name (ID),
  constraint UK_DA_CHANNEL_CODE unique (CODE)
);
comment on table IUNI_DA_CHANNEL is '渠道';
comment on column IUNI_DA_CHANNEL.CODE is '渠道代码';
comment on column IUNI_DA_CHANNEL.NAME is '渠道名称';

/*==============================================================*/
/* Table: IUNI_DA_WEBKPI                                        */
/*==============================================================*/
create table IUNI_DA_WEBKPI
(
  ID                   INT                  not null,
  TIME                 DATE                 not null,
  TTYPE                VARCHAR2(4)          not null
    constraint CKC_WEBKPI_TTYPE check (TTYPE in ('yyyy','MM','dd','HH','mm','ss','week')),
  PV                   INT                  not null,
  UV                   INT                  not null,
  NEW_UV               INT,
  VV                   INT                  not null,
  IP                   INT                  not null,
  WORKDAY              INT                  default 1 not null
    constraint CKC_WEBKPI_WORKDAY check (WORKDAY in (0,1)),
  SOURCE_ID            INT,
  CHANNEL_ID           INT,
  STAY_TIME            NUMBER               not null,
  TOTAL_JUMP           INT                  not null,
  TOTAL_TIME           NUMBER               not null,
  TOTAL_SIZE           NUMBER               not null,
  COUNTRY              VARCHAR2(64)         not null,
  AREA                 VARCHAR2(64),
  PROVINCE             VARCHAR2(64),
  CITY                 VARCHAR2(64),
  COUNTY               VARCHAR2(64),
  ISP                  VARCHAR2(64),
  CREATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_WEBKPI primary name (ID)
);
comment on table IUNI_DA_WEBKPI is 'WEB指标分析结果表';
comment on column IUNI_DA_WEBKPI.TIME is '时间，精确到分。';
comment on column IUNI_DA_WEBKPI.TTYPE is '时间类型，yyyy（年），MM（月），week（周），dd（日），表示该天的PV，HH（小时），表示该小时的PV，mm（分）表示该分钟的PV。';
comment on column IUNI_DA_WEBKPI.PV is '浏览量';
comment on column IUNI_DA_WEBKPI.UV is '独立用户数量';
comment on column IUNI_DA_WEBKPI.NEW_UV is '新独立用户数量';
comment on column IUNI_DA_WEBKPI.VV is '访问次数';
comment on column IUNI_DA_WEBKPI.IP is '独立IP数量';
comment on column IUNI_DA_WEBKPI.WORKDAY is '是否为工作日，0为非工作日，1为工作日。';
comment on column IUNI_DA_WEBKPI.CHANNEL_ID is '渠道';
comment on column IUNI_DA_WEBKPI.STAY_TIME is '总页面停留时间，单位ms.';
comment on column IUNI_DA_WEBKPI.TOTAL_JUMP is '总跳出次数';
comment on column IUNI_DA_WEBKPI.TOTAL_TIME is '总响应时间，单位毫秒。';
comment on column IUNI_DA_WEBKPI.TOTAL_SIZE is '总请求大小，单位字节。';
comment on column IUNI_DA_WEBKPI.ISP is '网络服务提供商';

alter table IUNI_DA_WEBKPI add constraint FK_IUNI_DA_WEBKPI_SOURCE_ID foreign name (SOURCE_ID) references IUNI_DA_FLOW_SOURCE (ID);
alter table IUNI_DA_WEBKPI add constraint FK_WEBKPI_CHANNEL_ID foreign name (CHANNEL_ID) references IUNI_DA_CHANNEL (ID);

/*==============================================================*/
/* Table: IUNI_DA_WEBKPI_CLICK                                  */
/*==============================================================*/
create table IUNI_DA_WEBKPI_CLICK
(
  ID                   INT                  not null,
  TIME                 DATE                 not null,
  TTYPE                VARCHAR2(4)          not null
    constraint CKC_WEBKPI_CLICK_TTYPE check (TTYPE in ('yyyy','MM','dd','HH','mm','ss','week')),
  RTAG_ID              INT                  not null,
  COUNT                INT                  not null,
  CHANNEL_ID           INT,
  CREATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_WEBKPI_CLICK primary name (ID)
);
comment on table IUNI_DA_WEBKPI_CLICK is '用户访问页面按钮点击分析表';
comment on column IUNI_DA_WEBKPI_CLICK.TIME is '时间，精确到分。';
comment on column IUNI_DA_WEBKPI_CLICK.TTYPE is '时间类型，yyyy（年），MM（月），week（周），dd（日），表示该天的PV，HH（小时），表示该小时的PV，mm（分）表示该分钟的PV。';
comment on column IUNI_DA_WEBKPI_CLICK.RTAG_ID is 'RTAG';
comment on column IUNI_DA_WEBKPI_CLICK.COUNT is '浏览量';
comment on column IUNI_DA_WEBKPI_CLICK.CHANNEL_ID is '渠道';

alter table IUNI_DA_WEBKPI_CLICK add constraint FK_WEBKPI_CLICK_CHANNEL_ID foreign name (CHANNEL_ID) references IUNI_DA_CHANNEL (ID);
alter table IUNI_DA_WEBKPI_CLICK add constraint FK_WEBKPI_CLICK_RTAG_ID foreign name (RTAG_ID) references IUNI_DA_RTAG (ID);

/*==============================================================*/
/* Table: IUNI_DA_WEBKPI_PAGE                                   */
/*==============================================================*/
create table IUNI_DA_WEBKPI_PAGE
(
  ID                   INT                  not null,
  TIME                 DATE                 not null,
  TTYPE                VARCHAR2(4)          not null
    constraint CKC_WEBKPI_PAGE_TTYPE check (TTYPE in ('yyyy','MM','dd','HH','mm','ss','week')),
  PAGE                 VARCHAR2(4000)       not null,
  PV                   INT                  not null,
  UV                   INT                  not null,
  IP                   INT                  not null,
  VV                   INT                  not null,
  CHANNEL_ID           INT,
  CREATE_DATE          DATE                 not null,
  constraint PK_IUNI_DA_WEBKPI_PAGE primary name (ID)
);
comment on table IUNI_DA_WEBKPI_PAGE is '用户访问页面分析表';
comment on column IUNI_DA_WEBKPI_PAGE.TIME is '时间，精确到分。';
comment on column IUNI_DA_WEBKPI_PAGE.TTYPE is '时间类型，yyyy（年），MM（月），week（周），dd（日），表示该天的PV，HH（小时），表示该小时的PV，mm（分）表示该分钟的PV。';
comment on column IUNI_DA_WEBKPI_PAGE.PV is '浏览量';
comment on column IUNI_DA_WEBKPI_PAGE.UV is '独立用户数量';
comment on column IUNI_DA_WEBKPI_PAGE.IP is '独立IP数量';
comment on column IUNI_DA_WEBKPI_PAGE.VV is '访问次数';
comment on column IUNI_DA_WEBKPI_PAGE.CHANNEL_ID is '渠道';

alter table IUNI_DA_WEBKPI_PAGE add constraint FK_PAGE_WEBKPI_CHANNEL_ID foreign name (CHANNEL_ID) references IUNI_DA_CHANNEL (ID);

