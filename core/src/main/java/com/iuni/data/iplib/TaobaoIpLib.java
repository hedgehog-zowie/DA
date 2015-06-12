package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.iuni.data.Context;
import com.iuni.data.IpLib;
import com.iuni.data.conf.Configurable;
import com.iuni.data.exceptions.IuniDAIpException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TaobaoIpLib extends AbstractIpLib implements Configurable, IpLib {

    private static final Logger logger = LoggerFactory.getLogger(TaobaoIpLib.class);
    public final String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0";

    private final Gson gson = new Gson();
    private HttpClient hc = new DefaultHttpClient();

    private String libUrl;

    public TaobaoIpLib() {
    }

    public String getLibUrl() {
        return libUrl;
    }

    public void setLibUrl(String libUrl) {
        this.libUrl = libUrl;
    }

    @Override
    public void configure(Context context) {
        libUrl = context.getString("url");
        Preconditions.checkState(libUrl != null, "The parameter url must be specified");
    }

    @Override
    @Deprecated
    public void getIpInfo(IpInfo ipInfo) {
        logger.info("get ip info of {}", ipInfo);
    }

    @Override
    public IpInfo getIpInfo(String ipStr) {
        logger.debug("get ip info of {}", ipStr);
        ipStr = ipStr.trim();
        IpInfo ipInfo = new IpInfo();
        try {
            // 实例化HTTP方法
            HttpGet httpGet = new HttpGet(libUrl + ipStr);
//            httpGet.setHeader("User-Agent", UA);
            HttpEntity e = hc.execute(httpGet).getEntity();
            InputStream is = e.getContent();
            // 转换成IpInfo
            StringWriter sw = new StringWriter();
            IOUtils.copy(is, sw);
            is.close();
            String res = sw.toString();
            JSONObject resp = JSONObject.fromObject(res);
            if (resp.has("data")) {
                JSONObject data = resp.getJSONObject("data");
                return getIpinfoFromJsonStr(data.toString());
            }
        } catch (Exception e) {
            String errorStr = new StringBuilder(
                    "get ipinfo from taobao iplib error. url is:").append(libUrl)
                    .append(",err msg:").append(e.getMessage()).toString();
            logger.error(errorStr);
        }
        return ipInfo;
    }

    /**
     * 将json格式的字符串转换成IpInfo对象
     *
     * @param ipinfoStr
     * @return
     * @throws IuniDAIpException
     */
    private IpInfo getIpinfoFromJsonStr(String ipinfoStr) throws IuniDAIpException {
        IpInfo ipinfo;
        try {
            ipinfo = gson.fromJson(ipinfoStr, IpInfo.class);
        } catch (Exception e) {
            String errorStr = new StringBuilder("Transfer ipstr to ipinfo error. ipstr is:")
                    .append(ipinfoStr)
                    .append(",err msg:")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        return ipinfo;
    }
}
