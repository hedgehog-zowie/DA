-- select real referer
select t1.realReferer , t2.* from 
(select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, 
iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310

-- pv
select country, area, region, city, couty, isp, realReferer, count(*), count(distinct cookie_vk), count(distinct remote_addr), sum(request_time), sum(body_bytes_sent) from (
select t1.realReferer , t2.* from (select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 
and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310 )t 
group by country, area, region, city, couty, isp, realReferer
order by country, area, region, city, couty, isp, realReferer

-- uv
select country, area, region, city, couty, isp, realReferer, count(distinct cookie_vk) from (
select t1.realReferer , t2.* from (select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 
and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310 )t 
group by country, area, region, city, couty, isp, realReferer
order by country, area, region, city, couty, isp, realReferer

-- ip
select country, area, region, city, couty, isp, realReferer, count(distinct remote_addr) from (
select t1.realReferer , t2.* from (select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 
and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310 )t 
group by country, area, region, city, couty, isp, realReferer
order by country, area, region, city, couty, isp, realReferer

-- new uv
select country, area, region, city, couty, isp, realReferer, adId, count(distinct cookie_vk) newUv from (
select country, area, region, city, couty, isp, realReferer, adId, TT2.cookie_vk from 
(select t1.realReferer , t2.* from 
(select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, 
iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310) TT1, 
(select distinct(cookie_vk) from iunilogofday where time < 20150309) TT2
where TT1.cookie_vk = TT2.cookie_vk) NEWUV
group by country, area, region, city, couty, isp, realReferer, adId
order by country, area, region, city, couty, isp, realReferer, adId

-- vv
select country, area, region, city, couty, isp, realReferer, count(*), sum(stayTime) from (
select country, area, region, city, couty, isp, realReferer, count(*) num, max(`timestamp`) - min(`timestamp`) stayTime from (
select t1.realReferer , t2.* from 
(select cookie_sessionid, max(referer) as realReferer from iunilogofday where connection_requests = 1 and time >= 20150309 and time < 20150310 group by cookie_sessionid) t1, 
iunilogofday t2 where t1.cookie_sessionid = t2.cookie_sessionid and time >= 20150309 and time < 20150310) t 
group by country, area, region, city, couty, isp, cookie_sessionid, realReferer) ttt
group by country, area, region, city, couty, isp, realReferer
order by country, area, region, city, couty, isp, realReferer

-- 


