-- web, page and click kpi
alter table IUNI_DA_WEBKPI drop constraint FK_IUNI_DA_WEBKPI_SOURCE_ID;
drop table IUNI_DA_WEBKPI cascade constraints;
alter table IUNI_DA_WEBKPI_PAGE drop constraint FK_PAGE_WEBKPI_CHANNEL_ID;
drop table IUNI_DA_WEBKPI_PAGE cascade constraints;
alter table IUNI_DA_WEBKPI_CLICK drop constraint FK_WEBKPI_CLICK_CHANNEL_ID;
alter table IUNI_DA_WEBKPI_CLICK drop constraint FK_WEBKPI_CLICK_RTAG_ID;
drop table IUNI_DA_WEBKPI_CLICK cascade constraints;
alter table IUNI_DA_WEBKPI_CHANNEL drop constraint FK_WEBKPI_CHANNEL_CHANNEL_CODE;
drop table IUNI_DA_WEBKPI_CHANNEL cascade constraints;

-- channel
alter table IUNI_DA_CHANNEL drop constraint FK_CHANNEL_TYPE_ID;
drop table IUNI_DA_CHANNEL cascade constraints;
drop table IUNI_DA_CHANNEL_TYPE cascade constraints;

-- flow source and type
alter table IUNI_DA_FLOW_SOURCE drop constraint FK_FLOW_SOURCE_TYPE_ID;
drop table IUNI_DA_FLOW_SOURCE cascade constraints;
drop table IUNI_DA_FLOW_SOURCE_TYPE cascade constraints;

-- holiday and type
alter table IUNI_DA_HOLIDAY drop constraint FK_IUNI_DA_HOLIDAY_TYPE_ID;
drop table IUNI_DA_HOLIDAY cascade constraints;
drop table IUNI_DA_HOLIDAY_TYPE cascade constraints;

-- register page
drop table IUNI_DA_REGISTER_PAGE cascade constraints;

-- advertisement
drop table IUNI_DA_AD cascade constraints;

-- chain step
alter table IUNI_DA_CHAIN_STEP drop constraint FK_CHAIN_STEP_RTAG_ID;
alter table IUNI_DA_CHAIN_STEP drop constraint FK_CHAIN_STEP_CHAIN_ID;
alter table IUNI_DA_CHAIN_STEP drop constraint FK_CHAIN_STEP_TYPE_ID;
drop table IUNI_DA_CHAIN_STEP cascade constraints;
-- step type
drop table IUNI_DA_CHAIN_STEP_TYPE cascade constraints;
-- chain
drop table IUNI_DA_CHAIN cascade constraints;

-- rTag and rTag type
alter table IUNI_DA_RTAG drop constraint FK_RTAT_TYPE_ID;
drop table IUNI_DA_RTAG cascade constraints;
drop table IUNI_DA_RTAG_TYPE cascade constraints;

-- user defin
drop table IUNI_DA_USER_DEFINED_REPORT cascade constraints;
drop table IUNI_DA_USER_CHAIN cascade constraints;

-- system constants
alter table IUNI_DA_SYSTEM_CONSTANTS_VALUE drop constraint FK_SYSTEM_CONSTANTS_VALUE_CID;
drop table IUNI_DA_SYSTEM_CONSTANTS_VALUE cascade constraints;
drop table IUNI_DA_SYSTEM_CONSTANTS cascade constraints;
