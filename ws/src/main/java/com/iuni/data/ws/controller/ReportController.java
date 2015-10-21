package com.iuni.data.ws.controller;

import com.iuni.data.common.Constants;
import com.iuni.data.common.DataType;
import com.iuni.data.hbase.field.CgiField;
import com.iuni.data.hbase.field.ClickField;
import com.iuni.data.hbase.field.CommonField;
import com.iuni.data.hbase.field.PageField;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.HttpUtils;
import com.iuni.data.ws.common.Config;
import com.iuni.data.ws.common.CookieKey;
import com.iuni.data.ws.common.ReturnCode;
import com.iuni.data.utils.CookieUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@RestController
@RequestMapping("/log/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    public static AtomicLong atl = new AtomicLong();

    @RequestMapping(produces = "application/json; charset=utf-8")
    @ResponseBody
    public String receiveData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        ReturnCode returnCode = null;
        if (type == null)
            returnCode = new ReturnCode(ReturnCode.ERROR_TYPE, ReturnCode.ERROR_TYPE_MSG);
        else {
            boolean flag = true;
            if (DataType.PAGE.getName().equals(type)) {
                String urlStr = request.getParameter(PageField.url.getRealFiled());
                if (StringUtils.isBlank(urlStr)) {
                    flag = false;
                    returnCode = new ReturnCode(ReturnCode.ERROR_PARAM, ReturnCode.ERROR_PARAM_MSG_PAGE);
                }
            } else if (DataType.CLICK.getName().equals(type)) {
                String urlStr = request.getParameter(ClickField.url.getRealFiled());
                String rTagStr = request.getParameter(ClickField.rTag.getRealFiled());
                if (StringUtils.isBlank(rTagStr) || StringUtils.isBlank(urlStr)) {
                    flag = false;
                    returnCode = new ReturnCode(ReturnCode.ERROR_PARAM, ReturnCode.ERROR_PARAM_MSG_CLICK);
                }
            } else if (DataType.CGI.getName().equals(type)) {
                String urlStr = request.getParameter(CgiField.url.getRealFiled());
                String stateStr = request.getParameter(CgiField.state.getRealFiled());
                String timeStr = request.getParameter(CgiField.time.getRealFiled());
                if (StringUtils.isBlank(urlStr) || StringUtils.isBlank(stateStr) || StringUtils.isBlank(timeStr) || !StringUtils.isNumeric(timeStr)) {
                    flag = false;
                    returnCode = new ReturnCode(ReturnCode.ERROR_PARAM, ReturnCode.ERROR_PARAM_MSG_CGI);
                }
            } else {
                flag = false;
                returnCode = new ReturnCode(ReturnCode.ERROR_TYPE, ReturnCode.ERROR_TYPE_MSG);
            }

            if (flag) {
                String ip = HttpUtils.getRealIP(request);
                String vk = "";
                String sid = "";
                String uid = "";
                String adId = "";
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie c = cookies[i];
                        if (CookieKey.VK.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                            vk = c.getValue();
                            logger.debug("cookies vk: {}", vk);
                        } else if (CookieKey.SID.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                            sid = c.getValue();
                            logger.debug("cookies sid: {}", sid);
                        } else if (CookieKey.UID.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                            uid = c.getValue();
                            logger.debug("cookies uid: {}", uid);
                        } else if (CookieKey.ADID.getName().toLowerCase().equals(c.getName().toLowerCase())) {
                            adId = c.getValue();
                            logger.debug("cookies adId: {}", adId);
                        }
                    }
                }
                if (StringUtils.isBlank(vk)) {
                    vk = getUvCookieVal(ip);
                    CookieUtil.addToCookie(request, response, CookieKey.VK.getName(), vk, Integer.MAX_VALUE);
                }
                if (StringUtils.isBlank(sid)) {
                    sid = getVvCookieVal();
                    CookieUtil.addToCookie(request, response, CookieKey.SID.getName(), sid);
                }
                // save report data to hbase
                saveData(type, vk, ip, sid, uid, adId, request.getParameterMap());
            }
        }

        if (returnCode == null)
            returnCode = new ReturnCode(ReturnCode.SUCCESS, ReturnCode.SUCCESS_MSG);

        JSONArray jsonArray = JSONArray.fromObject(returnCode);
        String resultStr = jsonArray.toString();
        String dType = request.getParameter("dType");
        if ("jsonp".equals(dType)) {
            String callBack = request.getParameter("callback") == null ? "" : request.getParameter("callback");
            resultStr = new StringBuilder(callBack).append("(").append(resultStr).append(")").toString();
        }
        logger.debug("return data: {}", resultStr);
        return resultStr;
    }

    private void saveData(String type, String vk, String ip, String sid, String uid, String adId, Map<String, String[]> paramMap) throws Exception {
        if (Config.getConf() == null) {
            throw new Exception("save data error. please check hbase config.");
        }
        try {
            long i = atl.incrementAndGet();
            if (i >= 99999)
                atl.set(0);
            String rowKey = new StringBuilder(type).append(System.currentTimeMillis()).append(String.format("%05d", i)).toString();
            Put put = new Put(Bytes.toBytes(rowKey));
            long timestamp = System.currentTimeMillis();
            StringBuffer dataSb = new StringBuffer();
            if (StringUtils.isNotEmpty(type)) {
                dataSb.append("\"" + CommonField.TYPE.getRealFiled() + "\":" + type + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.TYPE.getRealFiled()), timestamp, Bytes.toBytes(type));
            }
            if (StringUtils.isNotEmpty(vk)) {
                dataSb.append("\"" + CommonField.VK.getRealFiled() + "\":" + vk + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.VK.getRealFiled()), timestamp, Bytes.toBytes(vk));
            }
            if (StringUtils.isNotEmpty(ip)) {
                dataSb.append("\"" + CommonField.IP.getRealFiled() + "\":" + ip + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.IP.getRealFiled()), timestamp, Bytes.toBytes(ip));
            }
            if (StringUtils.isNotEmpty(sid)) {
                dataSb.append("\"" + CommonField.SID.getRealFiled() + "\":" + sid + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.SID.getRealFiled()), timestamp, Bytes.toBytes(sid));
            }
            if (StringUtils.isNotEmpty(uid)) {
                dataSb.append("\"" + CommonField.UID.getRealFiled() + "\":" + uid + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.UID.getRealFiled()), timestamp, Bytes.toBytes(uid));
            }
            if (StringUtils.isNotEmpty(adId)) {
                dataSb.append("\"" + CommonField.ADID.getRealFiled() + "\":" + adId + ",");
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.ADID.getRealFiled()), timestamp, Bytes.toBytes(adId));
            }
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                // if param name is start with the column has been set
                if (entry.getKey().startsWith(Config.getColumn())) {
                    dataSb.append("\"" + entry.getKey() + "\":" + (entry.getValue()[0]) + ",");
                    put.add(Config.getCfName(), Bytes.toBytes(entry.getKey()), timestamp, Bytes.toBytes((entry.getValue()[0])));
                }
            }
            String dataStr = "{" + dataSb.substring(0, dataSb.length() - 1) + "}";
            logger.info(dataStr);

            // pv counter
            HTable table = new HTable(Config.getConf(), Config.getTableName());
            HTable uvTable = new HTable(Config.getConf(), Config.getUvTableName());
            HTable vvTable = new HTable(Config.getConf(), Config.getVvTableName());
            HTable ipTable = new HTable(Config.getConf(), Config.getIpTableName());
            String sdStr = DateUtils.dateToSimpleDateStr(new Date(timestamp), "yyyyMMdd");
            // 总数
            // pv counter
            countPv(table, type, sdStr);
            // uv counter
            countUv(table, uvTable, type, vk, sdStr, Constants.hbaseTable_qualifierTotal);
            // vv counter
            countVv(table, vvTable, type, sid, sdStr, Constants.hbaseTable_qualifierTotal);
            // ip counter
            countIp(table, ipTable, type, ip, sdStr, Constants.hbaseTable_qualifierTotal);

            // 按url/rtag计数
            String qualifier = null;
            if (DataType.PAGE.getName().equals(type)) {
                qualifier = (paramMap.get(PageField.url.getRealFiled()))[0];
            } else if (DataType.CLICK.getName().equals(type)) {
                qualifier = (paramMap.get(ClickField.rTag.getRealFiled()))[0];
            } else if (DataType.CGI.getName().equals(type)) {
                qualifier = (paramMap.get(CgiField.url.getRealFiled()))[0];
            }
            countPv(table, type, sdStr, qualifier);
            countUv(table, uvTable, type, vk, sdStr, qualifier);
            countVv(table, vvTable, type, sid, sdStr, qualifier);
            countIp(table, ipTable, type, ip, sdStr, qualifier);

            // 写入数据
            table.put(put);
            table.close();
        } catch (Exception e) {
            throw e;
        }
    }

    private void countPv(HTable table, String type, String dateStr) throws IOException {
        countPv(table, type, dateStr, null);
    }

    private void countPv(HTable table, String type, String dateStr, String qualifier) throws IOException {
        Get get = new Get(Bytes.toBytes(dateStr));
        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes(type), Bytes.toBytes(dateStr));
        // 总数
        if (value == null) {
            if (qualifier == null)
                table.incrementColumnValue(Bytes.toBytes(dateStr), Bytes.toBytes(type), Bytes.toBytes("pv-" + Constants.hbaseTable_qualifierTotal), 1);
            else
                table.incrementColumnValue(Bytes.toBytes(dateStr), Bytes.toBytes(type), Bytes.toBytes("pv-" + qualifier), 1);
        }
    }

    private void countUv(HTable table, HTable uvTable, String type, String vk, String dateStr, String qualifier) throws IOException {
        Get get = new Get(Bytes.toBytes(vk + "-" + dateStr));
        Result result = uvTable.get(get);
        byte[] value = result.getValue(Bytes.toBytes(type), Bytes.toBytes(qualifier));
        if (value == null)
            table.incrementColumnValue(Bytes.toBytes(dateStr), Bytes.toBytes(type), Bytes.toBytes("uv-" + qualifier), 1);
    }

    private void countVv(HTable table, HTable vvTable, String type, String sid, String dateStr, String qualifier) throws IOException {
        Get get = new Get(Bytes.toBytes(sid + "-" + dateStr));
        Result result = vvTable.get(get);
        byte[] value = result.getValue(Bytes.toBytes(type), Bytes.toBytes(qualifier));
        if (value == null)
            table.incrementColumnValue(Bytes.toBytes(dateStr), Bytes.toBytes(type), Bytes.toBytes("vv-" + qualifier), 1);
    }

    private void countIp(HTable table, HTable vvTable, String type, String ip, String dateStr, String qualifier) throws IOException {
        Get get = new Get(Bytes.toBytes(ip + "-" + dateStr));
        Result result = vvTable.get(get);
        byte[] value = result.getValue(Bytes.toBytes(type), Bytes.toBytes(qualifier));
        if (value == null)
            table.incrementColumnValue(Bytes.toBytes(dateStr), Bytes.toBytes(type), Bytes.toBytes("ip-" + qualifier), 1);
    }

    /**
     * 获取UV Cookie Value
     *
     * @return String
     */
    private String getUvCookieVal(String ip) {
        String uvCookieVal;
        long time = System.nanoTime();

        uvCookieVal = CookieUtil.genUvCookie(ip, time);
        if (uvCookieVal == null)
            uvCookieVal = String.valueOf(time);

        return uvCookieVal;
    }

    /**
     * 获取VV Cookie Value
     * @return
     */
    private String getVvCookieVal() {
        return UUID.randomUUID().toString();
    }

}
