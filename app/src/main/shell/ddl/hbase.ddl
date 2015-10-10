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

