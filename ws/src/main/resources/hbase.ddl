disable 'iunilog'
drop 'iunilog'
disable 'timestampIdx'
drop 'timestampIdx'
disable 'userIdx'
drop 'userIdx'
disable 'reqIdx'
drop 'reqIdx'

create 'iunilog', 'f', {NUMREGIONS => 100, SPLITALGO => 'HexStringSplit'}
create 'timestampIdx', 'f', {NUMREGIONS => 100, SPLITALGO => 'HexStringSplit'}
create 'userIdx', 'f', {NUMREGIONS => 100, SPLITALGO => 'HexStringSplit'}
create 'reqIdx', 'f', {NUMREGIONS => 100, SPLITALGO => 'HexStringSplit'}
-- 增加协处理器
alter 'iunilog', 'coprocessor'=>'hdfs:///hbase/coprocessor/da-common-1.0-SNAPSHOT.jar|com.iuni.data.analyze.hbase.sindex.IuniIndexRegionObserve|1001'
-- 删除协处理器
alter 'iunilog', METHOD => 'table_att_unset', NAME => 'coprocessor$1'

put 'iunilog', 'testRow', 'f:time', '1111111111'
put 'iunilog', 'testRow2', 'f:time', '1111111112'

get_counter 'timeIdx', 'totalRow', 'f:iCol', 0

deleteall 'iunilog', '9a3b7773-a7d2-46c7-a9cb-849c884cfce4'

create 'prd', 'f', SPLITS => ['pv', 'cgi', 'click']

get_counter 'prd', 'totalRow', 'f:iCol', 0

describe 'prd'

-- prd-t
describe 'prd_t'
disable 'prd_t'
drop 'prd_t'
create 'prd_t', 'f','pv','click','cgi', SPLITS => ['pv', 'cgi', 'click', '0']
-- 增加协处理器
alter 'prd_t', 'coprocessor'=>'hdfs:///hbase/coprocessor/da-core.jar|com.iuni.data.hbase.CounterRegionObserveDev|1001'
-- 删除协处理器
alter 'prd_t', METHOD => 'table_att_unset', NAME => 'coprocessor$1'
create 'uv_t', 'pv', 'cgi', 'click'
create 'vv_t', 'pv', 'cgi', 'click'
create 'ip_t', 'pv', 'cgi', 'click'
incr 'prd_t', '20150101', 'c_click:pv',1
incr 'prd_t', '20150101', 'c_click:uv',1
incr 'prd_t', '20150101', 'c_click:vv',1
incr 'prd_t', '20150101', 'c_click:ip',1
get_counter 'prd_t','20150101','c_click:pv',0
get_counter 'prd_t','20150101','c_click:uv',0
get_counter 'prd_t','20150101','c_click:vv',0
get_counter 'prd_t','20150101','c_click:ip',0
incr 'uv','xxxxuvxxxx', 'pv:20150101', 1
incr 'vv','xxxxvvxxxx', 'pv:20150101', 1
incr 'ip','127.0.0.1', 'pv:20150101', 1
get_counter 'uv', 'xxxxuvxxxx','pv:20150101',0
get_counter 'vv', 'xxxxvvxxxx','pv:20150101',0
get_counter 'ip', '127.0.0.1','pv:20150101',0
get_counter 'uv', 'phwQIndWeZ', 'click:20150928',0
get_counter 'vv', 'phwRzAmeH9', 'click:20150928',0
get_counter 'ip', '0:0:0:0:0:0:0:1', 'click:20150928',0
get_counter 'prd_t', '20151009', 'click:pv-3.0001.0001', 0

disable 'prd'
alter 'prd', 'coprocessor'=>'hdfs:///hbase/coprocessor/da-core.jar|com.iuni.data.hbase.CounterRegionObserve|1001'
enable 'prd'

disable 'uv'
disable 'vv'
disable 'ip'
drop 'uv'
drop 'vv'
drop 'ip'
create 'uv', 'pv', 'cgi', 'click'
create 'vv', 'pv', 'cgi', 'click'
create 'ip', 'pv', 'cgi', 'click'

alter 'prd', 'pv','click','cgi'
alter 'prd', 'coprocessor'=>'hdfs:///hbase/coprocessor/da-core.jar|com.iuni.data.hbase.CounterRegionObserve|1001'

get_counter 'prd', '20151010', 'click:pv-3.0001.0001', 0