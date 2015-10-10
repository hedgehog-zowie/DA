package com.iuni.data.utils;

import com.sun.jersey.core.util.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class CryptUtils {

    private static final String HMAC_SHA1 = "HmacSHA1";

    private static final String Algorithm = "DESede";

    /**
     * 获取MD5 结果字符串
     *
     * @param source
     * @return
     */
    public static String encode(byte[] source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * hmac(data + timestamp)
     *
     * @param data
     * @param timestamp
     * @param secret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String generateAlipaySign(String data, String timestamp, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        data = data + timestamp;
        return hmacSha1(data.getBytes(), secret.getBytes());
    }

    /**
     * stringA="key1=value1&key2=value2"
     * stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d"
     * sign=MD5(stringSignTemp).toUpperCase()
     * @param params
     * @return
     */
    public static String generateWeixinSign(Map<String, String> params, String key) {
        // sort by key
        Map<String, String> sortedMap = new TreeMap<>(params);
        //
        StringBuffer signSb = new StringBuffer();
        String oldSign;
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            // check value is not blank
            if (StringUtils.isBlank(entry.getValue()))
                continue;
            if ("sign".equals(entry.getKey())) {
                oldSign = entry.getValue();
                continue;
            }
            // joint every key and value
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String signStr = signSb.append("key").append("=").append(key).toString();
        return encode(signStr.getBytes()).toUpperCase();
    }

    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 生成MD5编码的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String hmacSha1(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return encode(rawHmac);
    }

    /**
     * 3des解码
     *
     * @param value 待解密字符串
     * @param key   原始密钥字符串
     * @return
     * @throws Exception
     */
    public static String decrypt3DES(String value, String key) throws Exception {
        byte[] b = decryptMode(getKeyBytes(key), Base64.decode(value));
        return new String(b);
    }

    /**
     * 3des加密
     *
     * @param value 待加密字符串
     * @param key   原始密钥字符串
     * @return
     * @throws Exception
     */
    public static String encrypt3DES(String value, String key) throws Exception {
        String str = byte2Base64(encryptMode(getKeyBytes(key), value.getBytes()));
        return str;
    }

    /**
     * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
     *
     * @param strKey
     * @return
     * @throws Exception
     */
    public static byte[] getKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bkey = alg.digest();
//        System.out.println("md5key.length=" + bkey.length);
//        System.out.println("md5key=" + byte2hex(bkey));
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }
        for (int i = start; i < 24; i++) {//为了与.net16位key兼容
            bkey24[i] = bkey[i - start];
        }
//        System.out.println("byte24key.length=" + bkey24.length);
//        System.out.println("byte24key=" + byte2hex(bkey24));
        return bkey24;
    }

    /**
     * 加密
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     被加密的数据缓冲区（源）
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     加密后的缓冲区
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成base64编码
     *
     * @param b
     * @return
     */
    public static String byte2Base64(byte[] b) {
        return new String(Base64.encode(b));
    }

    /**
     * 转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

}
