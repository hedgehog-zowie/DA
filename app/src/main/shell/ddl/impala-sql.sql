select adId, url, count(*) from iunilog where 1 = 1  and year = 2015 and month = 03 and day = 17 and hour = 10 and minute = 12 group by adId, url;
select adId, url, count(distinct cookie_vk) from iunilog where 1 = 1  and year = 2015 and month = 03 and day = 17 and hour = 10 and minute = 11 group by adId, url;

select count(*) from iunilog where 1 = 1  and year = 2015 and month = 03 and day = 17 and hour = 10 and minute = 11;

Missing tables were not received

