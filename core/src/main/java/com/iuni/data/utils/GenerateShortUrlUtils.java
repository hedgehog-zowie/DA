package com.iuni.data.utils;

import com.taobao.api.internal.util.WebUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class GenerateShortUrlUtils {

    private static final String baiduShortUrlApi = "http://dwz.cn/create.php";

    public static String generateBaiduShortUrl(String url) {
        Map<String, String> params = new HashMap<>();
        params.put("url", url);
        BaiduShortUrlResponse baiduShortUrlResponse = null;
        try {
            baiduShortUrlResponse = JsonUtils.fromJson(WebUtils.doPost(baiduShortUrlApi, params, 3000, 10000), BaiduShortUrlResponse.class);
        } catch (IOException e) {
            return e.getMessage();
        }
        return baiduShortUrlResponse.getStatus() == 0 ? baiduShortUrlResponse.getTinyurl() : baiduShortUrlResponse.getError_msg();
    }

    private class BaiduShortUrlResponse {
        private int status;
        private String error_msg;
        private String longurl;
        private String tinyurl;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }

        public String getLongurl() {
            return longurl;
        }

        public void setLongurl(String longurl) {
            this.longurl = longurl;
        }

        public String getTinyurl() {
            return tinyurl;
        }

        public void setTinyurl(String tinyurl) {
            this.tinyurl = tinyurl;
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println(generateBaiduShortUrl("http://www.baidu.com"));
    }

}
