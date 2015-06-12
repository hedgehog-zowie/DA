use iuni;

drop table iuniLog;

CREATE EXTERNAL TABLE iuniLog (
  timestamp BIGINT,
  host STRING,
  method STRING,
  url STRING,
  protocal STRING,
  adId STRING,
  referer STRING,
  country STRING,
  area STRING,
  region STRING,
  city STRING,
  couty STRING,
  isp STRING,
  time_local STRING,
  connection INT,
  connection_requests INT,
  request_time FLOAT,
  remote_addr STRING,
  remote_user STRING,
  server_name STRING,
  request STRING,
  status STRING,
  body_bytes_sent INT,
  bytes_sent INT,
  sent_http_content_length INT,
  http_referer STRING,
  http_user_agent STRING,
  http_x_forwarded_for STRING,
  http_cookie STRING,
  http_via STRING,
  cookie_vk STRING,
  cookie_uid STRING,
  cookie_sessionid STRING
)
PARTITIONED BY (time INT)
ROW FORMAT DELIMITED
LOCATION '/flume/logs/use=usefull';

ALTER TABLE iuniLog ADD PARTITION (time=20150301);
ALTER TABLE iuniLog ADD PARTITION (time=20150302);
ALTER TABLE iuniLog ADD PARTITION (time=20150303);
ALTER TABLE iuniLog ADD PARTITION (time=20150304);
ALTER TABLE iuniLog ADD PARTITION (time=20150305);
ALTER TABLE iuniLog ADD PARTITION (time=20150306);
ALTER TABLE iuniLog ADD PARTITION (time=20150307);
ALTER TABLE iuniLog ADD PARTITION (time=20150308);
ALTER TABLE iuniLog ADD PARTITION (time=20150309);
ALTER TABLE iuniLog ADD PARTITION (time=20150310);
ALTER TABLE iuniLog ADD PARTITION (time=20150311);
ALTER TABLE iuniLog ADD PARTITION (time=20150312);
ALTER TABLE iuniLog ADD PARTITION (time=20150313);
ALTER TABLE iuniLog ADD PARTITION (time=20150314);
ALTER TABLE iuniLog ADD PARTITION (time=20150315);
ALTER TABLE iuniLog ADD PARTITION (time=20150316);
ALTER TABLE iuniLog ADD PARTITION (time=20150317);
ALTER TABLE iuniLog ADD PARTITION (time=20150318);
ALTER TABLE iuniLog ADD PARTITION (time=20150319);
ALTER TABLE iuniLog ADD PARTITION (time=20150320);
