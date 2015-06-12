package com.iuni.data.ws.controller;

import com.iuni.data.ws.common.Config;
import com.iuni.data.ws.common.CookieKey;
import com.iuni.data.ws.common.DataType;
import com.iuni.data.ws.common.ReturnCode;
import com.iuni.data.ws.common.field.CgiField;
import com.iuni.data.ws.common.field.ClickField;
import com.iuni.data.ws.common.field.CommonField;
import com.iuni.data.ws.common.field.PageField;
import com.iuni.data.ws.util.CookieUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
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
    public String receiveData(HttpServletRequest request, HttpServletResponse response) {
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
                String vk = null;
                String sid = null;
                String uid = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie c = cookies[i];
                        if (CookieKey.VK.getName().equals(c.getName())) {
                            vk = c.getValue();
                            logger.debug("cookies vk: {}", vk);
                        } else if (CookieKey.SID.getName().equals(c.getName())) {
                            sid = c.getValue();
                            logger.debug("cookies sid: {}", cookies);
                        } else if (CookieKey.UID.getName().equals(c.getName())) {
                            uid = c.getValue();
                            logger.debug("cookies uid: {}", cookies);
                        }
                    }
                    if (StringUtils.isBlank(vk))
                        vk = getUvCookieVal();
                    if (StringUtils.isBlank(sid))
                        sid = getUvCookieVal();
                    CookieUtil.addToCookie(request, response, CookieKey.VK.getName(), vk);
                    CookieUtil.addToCookie(request, response, CookieKey.SID.getName(), sid);
                }
                // save report data to hbase
                saveData(type, vk, sid, uid, request.getParameterMap());
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
        logger.info("return data: {}", resultStr);
        return resultStr;
    }

    private void saveData(String type, String vk, String sid, String uid, Map<String, String[]> paramMap) {
        if (Config.getConf() == null) {
            logger.error("save data error. please check hbase config.");
            return;
        }
        try {
            HTable table = new HTable(Config.getConf(), Config.getTableName());
            String rowKey = new StringBuilder(type).append(System.currentTimeMillis()).append(atl.incrementAndGet()).toString();
            Put put = new Put(Bytes.toBytes(rowKey));
            if (StringUtils.isNotEmpty(type))
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.TYPE.getRealFiled()), Bytes.toBytes(type));
            if (StringUtils.isNotEmpty(vk))
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.VK.getRealFiled()), Bytes.toBytes(vk));
            if (StringUtils.isNotEmpty(sid))
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.SID.getRealFiled()), Bytes.toBytes(sid));
            if (StringUtils.isNotEmpty(uid))
                put.add(Config.getCfName(), Bytes.toBytes(CommonField.UID.getRealFiled()), Bytes.toBytes(uid));
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                // if param name is start with the column has been set
                if (entry.getKey().startsWith(Config.getColumn()))
                    put.add(Config.getCfName(), Bytes.toBytes(entry.getKey()), Bytes.toBytes((entry.getValue()[0])));
            }
            table.put(put);
            table.close();
        } catch (Exception e) {
            logger.error("save data error. {}" + e.getLocalizedMessage());
        }
    }

    /**
     * 获取UV Cookie Value
     *
     * @return String
     */
    private String getUvCookieVal() {
        String uvCookieVal;
        long time = System.nanoTime();

        uvCookieVal = CookieUtil.genUvCookie(time);
        if (uvCookieVal == null)
            uvCookieVal = String.valueOf(time);

        return uvCookieVal;
    }

}
