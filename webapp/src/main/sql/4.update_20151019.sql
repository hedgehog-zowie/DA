CREATE TABLE IUNI_DA_CHANNEL_TYPE
(
   ID                   INT                  not null,
   CODE                 VARCHAR2(2)          not null,
   NAME                 VARCHAR2(128)        not null,
   "DESC"               VARCHAR2(4000),
   STATUS               INT                  default 1 not null
      constraint CKC_STATUS_CHANNEL_TYPE check (STATUS in (0,1)),
   CANCEL_FLAG          INT                  default 0 not null
      constraint CKC_CANCEL_FLAG_CHANNEL_TYPE check (CANCEL_FLAG in (0,1)),
   CREATE_BY            VARCHAR2(128)        not null,
   CREATE_DATE          DATE                 not null,
   UPDATE_BY            VARCHAR2(128)        not null,
   UPDATE_DATE          DATE                 not null,
   constraint PK_IUNI_DA_CHANNEL_TYPE primary key (ID)
);
comment on table IUNI_DA_CHANNEL_TYPE is '渠道类型';
comment on column IUNI_DA_CHANNEL_TYPE.CODE is '渠道类型编号';
comment on column IUNI_DA_CHANNEL_TYPE.NAME is '渠道类型名称';
comment on column IUNI_DA_CHANNEL_TYPE."DESC" is '备注';
comment on column IUNI_DA_CHANNEL_TYPE.STATUS is '是否有效标识，0表示无效，1表示有效。';
comment on column IUNI_DA_CHANNEL_TYPE.CANCEL_FLAG is '逻辑删除标识，0表示未删除，1表示删除。';

alter table IUNI_DA_CHANNEL add type_id integer;

alter table IUNI_DA_CHANNEL
   add constraint FK_CHANNEL_TYPE_ID foreign key (TYPE_ID)
      references IUNI_DA_CHANNEL_TYPE (ID);

INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(1, 30, '30-EMD', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(2, 31, '31-EMD-邮件', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(3, 32, '32-EMD-短信', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(4, 40, '40-站外推广', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(5, 41, '41-腾讯广点通', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(6, 42, '42-聚效DSP', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(7, 43, '43-百度sem', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(8, 44, '44-什么值得买', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(9, 45, '45-趣分期', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(10, 46, '46-垂直媒体', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(11, 50, '50-活动', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);
INSERT INTO IUNI_DA_CHANNEL_TYPE VALUES(12, 61, '61-社区运营推广', '', 1, 0, 'admin', SYSDATE, 'admin', SYSDATE);

UPDATE IUNI_DA_CHANNEL IDC SET IDC.TYPE_ID = (SELECT ID FROM IUNI_DA_CHANNEL_TYPE IDCT WHERE IDCT.CODE = SUBSTR(IDC.CODE,0, 2)) WHERE IDC.STATUS = 1 AND IDC.CANCEL_FLAG = 0;



