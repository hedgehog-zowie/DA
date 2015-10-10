package com.iuni.data.hbase;

import com.iuni.data.common.Constants;
import com.iuni.data.common.DataType;
import com.iuni.data.hbase.field.CgiField;
import com.iuni.data.hbase.field.ClickField;
import com.iuni.data.hbase.field.CommonField;
import com.iuni.data.hbase.field.PageField;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.HttpUtils;
import com.iuni.data.utils.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class CounterRegionObserve extends BaseRegionObserver {

    private static final Logger logger = LoggerFactory.getLogger(CounterRegionObserve.class);

    private static final Configuration conf = new Configuration();

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        String rowKey = Bytes.toString(put.getRow());
        // counter column family name
        String type = "";
        String timestampStr = "";
        String qualifier = "";
        if (rowKey.startsWith(DataType.PAGE.getName())) {
            type = rowKey.substring(0, 2);
            timestampStr = rowKey.substring(2, 15);
            List<Cell> pageCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(PageField.url.getRealFiled()));
            if (pageCells.size() != 0) {
                String url = Bytes.toString(CellUtil.cloneValue(pageCells.get(0)));
                qualifier = HttpUtils.getUrlNoParam(HttpUtils.decode(url, "UTF-8"));
            }
        } else if (rowKey.startsWith(DataType.CGI.getName())) {
            type = rowKey.substring(0, 3);
            timestampStr = rowKey.substring(3, 16);
            List<Cell> cgiCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(CgiField.url.getRealFiled()));
            if (cgiCells.size() != 0) {
                String url = Bytes.toString(CellUtil.cloneValue(cgiCells.get(0)));
                qualifier = HttpUtils.getUrlNoParam(HttpUtils.decode(url, "UTF-8"));
            }
        } else if (rowKey.startsWith(DataType.CLICK.getName())) {
            type = rowKey.substring(0, 5);
            timestampStr = rowKey.substring(5, 18);
            List<Cell> clickCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(ClickField.rTag.getRealFiled()));
            if (clickCells.size() != 0)
                qualifier = Bytes.toString(CellUtil.cloneValue(clickCells.get(0)));
        }
        // counter column name
        String dayTimeStr = DateUtils.dateToSimpleDateStr(new Date(Long.parseLong(timestampStr)), "yyyyMMdd");

        HTable uvTable = new HTable(conf, Constants.hbaseTable_UV_DEFAULT);
        List<Cell> uvCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(CommonField.VK.getRealFiled()));
        if (uvCells.size() != 0) {
            Iterator<Cell> iterator = uvCells.iterator();
            while (iterator.hasNext()) {
                Cell tmp = iterator.next();
                String uv = Bytes.toString(CellUtil.cloneValue(tmp));
                if (StringUtils.isBlank(uv))
                    continue;
                //分url/rtag计数， rowkey = uv-dayTimeStr, family=type, qualifier = url/rtag
                long uvCnt = uvTable.incrementColumnValue(Bytes.toBytes(uv + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(qualifier), 1);
                logger.info("uv counter {} {}:{} is {}.", uv + "-" + dayTimeStr, type, qualifier, uvCnt);
                //总数
                long uvCntT = uvTable.incrementColumnValue(Bytes.toBytes(uv + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(Constants.hbaseTable_qualifierTotal), 1);
                logger.info("uv counter {} {}:{} is {}.", uv + "-" + dayTimeStr, type, Constants.hbaseTable_qualifierTotal, uvCntT);
            }
        }

        HTable vvTable = new HTable(conf, Constants.hbaseTable_VV_DEFAULT);
        List<Cell> vvCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(CommonField.SID.getRealFiled()));
        if (vvCells.size() != 0) {
            Iterator<Cell> iterator = vvCells.iterator();
            while (iterator.hasNext()) {
                Cell tmp = iterator.next();
                String vv = Bytes.toString(CellUtil.cloneValue(tmp));
                if (StringUtils.isBlank(vv))
                    continue;
                //分url/rtag计数， rowkey = vv-dayTimeStr, family=type, qualifier = url/rtag
                long vvCnt = vvTable.incrementColumnValue(Bytes.toBytes(vv + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(qualifier), 1);
                logger.info("vv counter {} {}:{} is {}.", vv + "-" + dayTimeStr, type, qualifier, vvCnt);
                //总数
                long vvCntT = vvTable.incrementColumnValue(Bytes.toBytes(vv + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(Constants.hbaseTable_qualifierTotal), 1);
                logger.info("vv counter {} {}:{} is {}.", vv + "-" + dayTimeStr, type, Constants.hbaseTable_qualifierTotal, vvCntT);
            }
        }

        HTable ipTable = new HTable(conf, Constants.hbaseTable_IP_DEFAULT);
        List<Cell> ipCells = put.get(Bytes.toBytes(Constants.pageReportDataTableCfDefault), Bytes.toBytes(CommonField.IP.getRealFiled()));
        if (ipCells.size() != 0) {
            Iterator<Cell> iterator = ipCells.iterator();
            while (iterator.hasNext()) {
                Cell tmp = iterator.next();
                String ip = Bytes.toString(CellUtil.cloneValue(tmp));
                if (StringUtils.isBlank(ip))
                    continue;
                //分url/rtag计数 rowkey = ip-dayTimeStr, family=type, qualifier = url/rtag
                long ipCnt = ipTable.incrementColumnValue(Bytes.toBytes(ip + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(qualifier), 1);
                logger.info("ip counter {} {}:{} is {}.", ip + "-" + dayTimeStr, type, qualifier, ipCnt);
                //总数
                long ipCntT = ipTable.incrementColumnValue(Bytes.toBytes(ip + "-" + dayTimeStr), Bytes.toBytes(type), Bytes.toBytes(Constants.hbaseTable_qualifierTotal), 1);
                logger.info("ip counter {} {}:{} is {}.", ip + "-" + dayTimeStr, type, Constants.hbaseTable_qualifierTotal, ipCntT);
            }
        }

    }
}
