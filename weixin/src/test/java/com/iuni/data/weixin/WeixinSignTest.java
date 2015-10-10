package com.iuni.data.weixin;

import com.iuni.data.utils.CryptUtils;
import com.taobao.api.internal.util.WebUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class WeixinSignTest {

    public static void main(String args[]) throws Exception {
//        String[] dates = {"20150518", "20150519", "20150521", "20150721",
//                "20150801", "20150802", "20150803", "20150804", "20150805", "20150806",
//                "20150807", "20150808", "20150809", "20150810", "20150811", "20150812"};
        String[] dates = {"20150812", "20150813"};
        for (String date : dates) {
            System.out.println("==============" + date + " begin================");
            Map<String, String> params = new HashMap<>();
            params.put("appid", "wxa47e81ec14fc7dad");
            params.put("mch_id", "10011092");
            params.put("nonce_str", "21df7dc9cd8616b56919f20d9f679233");
            params.put("bill_date", date);
            params.put("bill_type", "ALL");
            String key = "c69b0e22f619ff836b71be134092341f";
            String sign = CryptUtils.generateWeixinSign(params, key);
//            System.out.println(sign);
            params.put("sign", sign);
            StringBuilder sb = new StringBuilder("<xml>");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String nodeHead = "<" + entry.getKey() + ">";
                String nodeTail = "</" + entry.getKey() + ">";
                sb.append(nodeHead).append(entry.getValue()).append(nodeTail);
            }
            sb.append("</xml>");
            String datastr = sb.toString();
//        String datastr = "<xml>\n" +
//                "  <appid>wxa47e81ec14fc7dad</appid>\n" +
//                "  <bill_date>20150806</bill_date>\n" +
//                "  <bill_type>ALL</bill_type>\n" +
//                "  <mch_id>10011092</mch_id>\n" +
//                "  <nonce_str>21df7dc9cd8616b56919f20d9f679233</nonce_str>\n" +
//                "  <sign>586EA107C07A54A2329CB29600A007EE</sign>\n" +
//                "</xml>";
//        WebUtils.setIgnoreSSLCheck(true);
//        System.out.println(WebUtils.doPost("https://api.mch.weixin.qq.com/pay/downloadbill", "application/x-www-form-urlencoded;charset=UTF-8", URLEncoder.encode(datastr, "UTF-8").getBytes("UTF-8"), 3000, 10000));

            URL url = new URL("https://api.mch.weixin.qq.com/pay/downloadbill");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            SSLContext ctx = SSLContext.getInstance("SSL", "SunJSSE");
            ctx.init(null, new TrustManager[]{new WebUtils.TrustAllTrustManager()}, new SecureRandom());
            conn.setSSLSocketFactory(ctx.getSocketFactory());
//        conn.setHostnameVerifier(new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream out = conn.getOutputStream();
            out.write(datastr.getBytes("UTF-8"));
            InputStream inputStream = conn.getInputStream();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            StringBuilder response = new StringBuilder();
            final char[] buff = new char[1024];
            int read;
            while ((read = reader.read(buff)) > 0)
                response.append(buff, 0, read);
            System.out.println(response.toString());
            System.out.println("==============" + date + " end================");
        }
    }

}
