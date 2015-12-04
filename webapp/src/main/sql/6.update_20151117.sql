-- drop
alter table IUNI_DA_BURIED_POINT_RELATION drop constraint FK_BURIED_POINT_RELATION;
alter table IUNI_DA_BURIED_POINT_RELATION drop constraint FK_BURIED_GROUP_RELATION;
drop table IUNI_DA_BURIED_POINT_RELATION cascade constraints;
drop table IUNI_DA_BURIED_POINT cascade constraints;
drop table IUNI_DA_BURIED_GROUP cascade constraints;

-- create
/*==============================================================*/
/* Table: IUNI_DA_BURIED_POINT                                  */
/*==============================================================*/
create table IUNI_DA_BURIED_POINT
(
   ID                   INT                  not null,
   WEBSITE_CODE         VARCHAR2(32)         not null,
   WEBSITE              VARCHAR2(128)        not null,
   PAGE_NAME            VARCHAR2(32)         not null,
   PAGE_POSITION        VARCHAR2(128)        not null,
   POINT_FLAG           VARCHAR2(32)         not null,
   POINT_TYPE           INT,
   "DESC"               VARCHAR2(4000),
   STATUS               INT                  default 1 not null
      constraint CKC_STATUS_BURIED_POINT check (STATUS in (0,1)),
   CANCEL_FLAG          INT                  default 0 not null
      constraint CKC_CANCEL_FLAG_BURIED_POINT check (CANCEL_FLAG in (0,1)),
   CREATE_BY            VARCHAR2(128)        not null,
   CREATE_DATE          DATE                 not null,
   UPDATE_BY            VARCHAR2(128)        not null,
   UPDATE_DATE          DATE                 not null,
   constraint PK_IUNI_DA_BURIED_POINT primary key (ID)
);
comment on table IUNI_DA_BURIED_POINT is '埋点定义表';
comment on column IUNI_DA_BURIED_POINT.WEBSITE_CODE is '站点信息编码';
comment on column IUNI_DA_BURIED_POINT.WEBSITE is '站点信息';
comment on column IUNI_DA_BURIED_POINT.PAGE_NAME is '站点具体页面信息';
comment on column IUNI_DA_BURIED_POINT.PAGE_POSITION is '页面具体位置信息';
comment on column IUNI_DA_BURIED_POINT.POINT_FLAG is '埋点标示';
comment on column IUNI_DA_BURIED_POINT.POINT_TYPE is '埋点类型';
comment on column IUNI_DA_BURIED_POINT."DESC" is '备注';
comment on column IUNI_DA_BURIED_POINT.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_BURIED_POINT.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
/*==============================================================*/
/* Table: IUNI_DA_BURIED_GROUP                                  */
/*==============================================================*/
create table IUNI_DA_BURIED_GROUP
(
   ID                   INT                  not null,
   NAME                 VARCHAR2(32)         not null,
   "DESC"               VARCHAR2(4000),
   STATUS               INT                  default 1 not null
      constraint CKC_STATUS_BURIED_GROUP check (STATUS in (0,1)),
   CANCEL_FLAG          INT                  default 0 not null
      constraint CKC_CANCEL_FLAG_BURIED_GROUP check (CANCEL_FLAG in (0,1)),
   CREATE_BY            VARCHAR2(128)        not null,
   CREATE_DATE          DATE                 not null,
   UPDATE_BY            VARCHAR2(128)        not null,
   UPDATE_DATE          DATE                 not null,
   constraint PK_IUNI_DA_BURIED_GROUP primary key (ID)
);
comment on table IUNI_DA_BURIED_GROUP is '埋点组定义表';
comment on column IUNI_DA_BURIED_GROUP.NAME is '名称';
comment on column IUNI_DA_BURIED_GROUP."DESC" is '描述';
comment on column IUNI_DA_BURIED_GROUP.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_BURIED_GROUP.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
/*==============================================================*/
/* Table: IUNI_DA_BURIED_POINT_RELATION                         */
/*==============================================================*/
create table IUNI_DA_BURIED_POINT_RELATION
(
   ID                   INT                  not null,
   BURIED_POINT_ID      INT,
   BURIED_GROUP_ID      INT,
   "DESC"               VARCHAR2(4000),
   STATUS               INT                  default 1 not null
      constraint CKC_STATUS_BURIED_POINT_REL check (STATUS in (0,1)),
   CANCEL_FLAG          INT                  default 0 not null
      constraint CKC_CANCEL_FLAG_BURIED_REL check (CANCEL_FLAG in (0,1)),
   CREATE_BY            VARCHAR2(128)        not null,
   CREATE_DATE          DATE                 not null,
   UPDATE_BY            VARCHAR2(128)        not null,
   UPDATE_DATE          DATE                 not null,
   constraint PK_IUNI_DA_BURIED_POINT_RELATI primary key (ID)
);
comment on table IUNI_DA_BURIED_POINT_RELATION is '埋点组与埋点关系表';
comment on column IUNI_DA_BURIED_POINT_RELATION."DESC" is '备注';
comment on column IUNI_DA_BURIED_POINT_RELATION.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_BURIED_POINT_RELATION.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';
alter table IUNI_DA_BURIED_POINT_RELATION add constraint FK_BURIED_POINT_RELATION foreign key (BURIED_POINT_ID) references IUNI_DA_BURIED_POINT (ID);
alter table IUNI_DA_BURIED_POINT_RELATION add constraint FK_BURIED_GROUP_RELATION foreign key (BURIED_GROUP_ID) references IUNI_DA_BURIED_GROUP (ID);


