/*==============================================================*/
/* Table: IUNI_DA_SYSTEM_CONSTANTS                              */
/*==============================================================*/
create table IUNI_DA_SYSTEM_CONSTANTS
(
   ID                   INT                  not null,
   CODE                 VARCHAR2(64)         not null,
   NAME                 VARCHAR2(64)         not null,
   SYSTEM_CODE          VARCHAR2(64)         not null,
   "DESC"               VARCHAR2(1024),
   constraint PK_IUNI_DA_SYSTEM_CONSTANTS primary key (ID),
   constraint AK_UQ_SYSTEM_CONSTANT_IUNI_DA_ unique (CODE)
);
comment on table IUNI_DA_SYSTEM_CONSTANTS is '系统常量定义表';
comment on column IUNI_DA_SYSTEM_CONSTANTS.CODE is '常量编码';
comment on column IUNI_DA_SYSTEM_CONSTANTS.NAME is '常量名';
comment on column IUNI_DA_SYSTEM_CONSTANTS.SYSTEM_CODE is '所属系统编码';
comment on column IUNI_DA_SYSTEM_CONSTANTS."DESC" is '描述';

/*==============================================================*/
/* Table: IUNI_DA_SYSTEM_CONSTANTS_VALUE                        */
/*==============================================================*/
create table IUNI_DA_SYSTEM_CONSTANTS_VALUE
(
   ID                   INT                  not null,
   VALUE                VARCHAR2(64)         not null,
   EXPLAIN              VARCHAR2(64)         not null,
   CONSTANT_ID          INT                  not null,
   "DESC"               VARCHAR2(1024),
   constraint PK_IUNI_DA_SYSTEM_CONSTANTS_VA primary key (ID)
);
comment on table IUNI_DA_SYSTEM_CONSTANTS_VALUE is '系统常量值';
comment on column IUNI_DA_SYSTEM_CONSTANTS_VALUE.VALUE is '值';
comment on column IUNI_DA_SYSTEM_CONSTANTS_VALUE.EXPLAIN is '中文含意';
comment on column IUNI_DA_SYSTEM_CONSTANTS_VALUE.CONSTANTS_CODE is '所属常量';
comment on column IUNI_DA_SYSTEM_CONSTANTS_VALUE."DESC" is '描述';

alter table IUNI_DA_SYSTEM_CONSTANTS_VALUE add constraint FK_SYSTEM_CONSTANTS_VALUE_CID foreign key (CONSTANT_ID) references IUNI_DA_SYSTEM_CONSTANTS (ID);


/*==============================================================*/
/* Table: IUNI_DA_WEBKPI_CHANNEL                                */
/*==============================================================*/
create table IUNI_DA_WEBKPI_CHANNEL
(
   ID                   INT                  not null,
   TIME                 DATE                 not null,
   TTYPE                VARCHAR2(4)          not null
      constraint CKC_WEBKPI_CHANNEL_TTYPE check (TTYPE in ('yyyy','MM','dd','HH','mm','ss','week')),
   PV                   NUMBER               not null,
   UV                   NUMBER               not null,
   VV                   NUMBER               not null,
   IP                   NUMBER               not null,
   STAY_TIME            NUMBER               not null,
   TOTAL_JUMP           NUMBER               not null,
   CHANNEL_CODE         VARCHAR2(16)         not null,
   CREATE_DATE          DATE                 not null,
   constraint PK_IUNI_DA_WEBKPI_CHANNEL primary key (CODE, ID)
);
comment on table IUNI_DA_WEBKPI_CHANNEL is '按渠道分析WEB指标分析结果表';
comment on column IUNI_DA_WEBKPI_CHANNEL.TIME is '时间，精确到分。';
comment on column IUNI_DA_WEBKPI_CHANNEL.TTYPE is '时间类型，yyyy（年），MM（月），week（周），dd（日），表示该天的PV，HH（小时），表示该小时的PV，mm（分）表示该分钟的PV。';
comment on column IUNI_DA_WEBKPI_CHANNEL.PV is '浏览量';
comment on column IUNI_DA_WEBKPI_CHANNEL.UV is '独立用户数量';
comment on column IUNI_DA_WEBKPI_CHANNEL.VV is '访问次数';
comment on column IUNI_DA_WEBKPI_CHANNEL.IP is '独立IP数量';
comment on column IUNI_DA_WEBKPI_CHANNEL.STAY_TIME is '总页面停留时间，单位ms.';
comment on column IUNI_DA_WEBKPI_CHANNEL.TOTAL_JUMP is '总跳出次数';
comment on column IUNI_DA_WEBKPI_CHANNEL.CHANNEL_CODE is '渠道';
alter table IUNI_DA_WEBKPI_CHANNEL add constraint FK_WEBKPI_CHANNEL_CHANNEL_CODE foreign key (CHANNEL_CODE) references IUNI_DA_CHANNEL (CODE);


