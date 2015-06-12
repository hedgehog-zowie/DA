package com.iuni.data.iplib.iuni;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iuni.data.exceptions.IuniDAIpException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 从APNIC获取IP段 从TAOBAO IP库获取IP段详细信息，存入本地IP库文件
 * <p/>
 * 包括三个部分：头信息区，IP段区，IP段信息区
 * 1，头信息区，格式为first_start_ip_pos/last_start_ip_pos/storeSize/ipNum，格式说明：
 * first_start_ip_pos：第一个START_IP的位置，4字节
 * last_start_ip_pos：最后一个START_IP的位置，4字节
 * storeSize：IP段个数，4字节
 * ipNum：总IP个数，8字节
 * 2，IP段区，格式为start_ip/end_ip_pos，格式说明：
 * start_ip：存放起始IP地址，4字节
 * end_ip_pos：结束地址的位置，4字节
 * 3，IP段信息区，格式为end_ip/country/area/region/city/county/isp，
 * end_ip：结束IP地址，8字节
 * country：国家，不限长
 * area：地区，不限长
 * region：省，不限长
 * city：市，不限长
 * county：区/县，不限长
 * isp：网络服务提供商，不限长
 * <p/>
 * 使用方法：
 * IpStore ipRangeList = IpStore.getInstance(); ipRangeList.initIpStore();
 *
 * @author Nicholas
 */
public class IpStore {

    private static final Logger logger = LoggerFactory.getLogger(IpStore.class);
    public static final String afrinicUrl = "http://ftp.afrinic.net/pub/stats/afrinic/delegated-afrinic-latest";
    public static final String apnicUrl = "http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest";
    public static final String arinUrl = "http://ftp.arin.net/pub/stats/arin/delegated-arin-extended-latest";
    public static final String ripenccUrl = "http://ftp.ripe.net/ripe/stats/delegated-ripencc-latest";
    public static final String lacnicUrl = "http://ftp.lacnic.net/pub/stats/lacnic/delegated-lacnic-latest";

    public static final String afrinicPath = "/com/iuni/data/iplib/iuni/iplibrary/delegated-afrinic-latest";
    public static final String apnicPath = "/com/iuni/data/iplib/iuni/iplibrary/delegated-apnic-latest";
    public static final String arinPath = "/com/iuni/data/iplib/iuni/iplibrary/delegated-arin-extended-latest";
    public static final String ripenccPath = "/com/iuni/data/iplib/iuni/iplibrary/delegated-ripencc-latest";
    public static final String lacnicPath = "/com/iuni/data/iplib/iuni/iplibrary/delegated-lacnic-latest";

    public final String countryPath = "/com/iuni/data/iplib/iuni/iplibrary/all-country";

    private static final String splitStr = "[|]";
    private static final String ipType = "ipv4";
    private static final String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    private static final int COUNTRY_ID_INDEX = 1;
    private static final int IP_TYPE_INDEX = 2;
    private static final int START_IP_INDEX = 3;
    private static final int HOST_NUM_INDEX = 4;

    /**
     * 中国区标识
     */
    private static final String COUNTRY_ID_CHINA = "CN";

    /**
     * 头信息区长度
     */
    private static final int HEAD_FIELD_LENGTH = 20;
    /**
     * IP段区每条记录长度
     */
    private static final int IP_REGION_FIELD_LENGTH = 8;
    /**
     * IP信息区每条记录长度
     */
    // private final int IPINFO_REGION_FIELD_LENGTH = 8;

    public List<IpRange> ipRangeList = new ArrayList<IpRange>();

    private IpUtils ipUtils;

    private static IpStore instance;

    /**
     * 国家MAP，根据域名后缀查找国家名称
     */
    private final Map<String, Country> countryMap;

    private static final int minIpRangeSize = 256;
    private static int chinaIpRangeNum = 0;

    private IpStore() throws IuniDAIpException {
        ipUtils = IpUtils.getInstance();
        countryMap = new HashMap<String, Country>();
    }

    public static IpStore getInstance() throws IuniDAIpException {
        if (instance == null)
            instance = new IpStore();
        return instance;
    }

    /**
     * 获取ip范围
     *
     * @return
     * @throws IuniDAIpException
     */
    private void getIpRange() throws IuniDAIpException {
        String[] filePaths = {afrinicPath, apnicPath, arinPath, ripenccPath, lacnicPath};
        String[] urls = {afrinicUrl, apnicUrl, arinUrl, ripenccUrl, lacnicUrl};

        for (int i = 0; i < filePaths.length; i++) {
            String filePath = null;
            try {
                // filePath = IpStore.class.getResource(filePaths[i]).getPath();
                filePath = System.getProperty("user.dir") + filePaths[i];
            } catch (Exception e) {
                String errorStr = new StringBuilder(
                        "IpStore local filepath is not exsit:")
                        .append(filePaths[i]).append(". error msg: ")
                        .append(e.getMessage()).toString();
                logger.info(errorStr);
            }
            if (filePath != null)
                ipRangeList.addAll(getIpRange(filePath));
            else {
                URL url;
                try {
                    url = new URL(urls[i]);
                } catch (MalformedURLException e) {
                    String errorStr = new StringBuilder("IpStore get url error, url is:")
                            .append(urls[i])
                            .append(". error msg: ")
                            .append(e.getMessage())
                            .toString();
                    logger.error(errorStr);
                    throw new IuniDAIpException(errorStr);
                }
                URLConnection con;
                try {
                    con = url.openConnection();
                } catch (IOException e) {
                    String errorStr = new StringBuilder("IpStore open url error, url is:")
                            .append(urls[i])
                            .append(". error msg: ")
                            .append(e.getMessage())
                            .toString();
                    logger.error(errorStr);
                    throw new IuniDAIpException(errorStr);
                }
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    String errorStr = new StringBuilder("IpStore read input stream error, encoding is UTF-8. error msg: ")
                            .append(e.getMessage())
                            .toString();
                    logger.error(errorStr);
                    throw new IuniDAIpException(errorStr);
                } catch (IOException e) {
                    String errorStr = new StringBuilder("IpStore read input stream error, error msg: ")
                            .append(e.getMessage())
                            .toString();
                    logger.error(errorStr);
                    throw new IuniDAIpException(errorStr);
                }
                ipRangeList.addAll(getIpRange(reader));
            }
        }
        Collections.sort(ipRangeList, new IpComparator());
    }

    /**
     * 从文件中获取ip范围
     *
     * @return
     * @throws IuniDAIpException
     * @throws IOException
     */
    private List<IpRange> getIpRange(String filePath) throws IuniDAIpException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        } catch (FileNotFoundException e) {
            String errorStr = new StringBuilder(
                    "IpStore get ip range error, file not found, file path is:")
                    .append(filePath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        return getIpRange(reader);
    }

    private List<IpRange> getIpRange(BufferedReader reader)
            throws IuniDAIpException {
        String info = null;
        List<IpRange> ipRangeList = new ArrayList<IpRange>();
        try {
            while ((info = reader.readLine()) != null) {
                if (info.startsWith("afrinic|") || info.startsWith("apnic|")
                        || info.startsWith("arin|") || info.startsWith("lacnic|")
                        || info.startsWith("ripencc|")) {
                    String[] str = info.split(splitStr);
                    if (!"".equals(str[COUNTRY_ID_INDEX].trim())
                            && str[IP_TYPE_INDEX].equals(ipType)
                            && str[START_IP_INDEX].matches(ipRegex)) {
                        String startIpStr = str[START_IP_INDEX].trim();
                        byte[] startIp = ipUtils.getIpByteArrayFromString(startIpStr);
                        int hostNum = Integer.parseInt(str[HOST_NUM_INDEX].trim());
                        ipRangeList.add(new IpRange(startIpStr, startIp, hostNum, str[COUNTRY_ID_INDEX].trim()));
                        if (COUNTRY_ID_CHINA.equals(str[COUNTRY_ID_INDEX].trim())) {
                            chinaIpRangeNum += (hostNum / 256);
                        }
                    }
                }
            }
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore get ip range error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        try {
            reader.close();
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore close buffer error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        return ipRangeList;
    }

    /**
     * 通过ip从淘宝IP库中获取ip数据，转换成IpInfo
     *
     * @param ip
     * @return
     * @throws IOException
     * @throws IuniDAIpException
     */
    public IpInfo getIpInfoFromTaobao(String ip) throws IuniDAIpException {
        return ipUtils.getIpInfoFromTaobao(ip);
    }

    /**
     * 根据本地存储的countryId与country信息获取IpInfo，只包含国家信息，用来处理中国以外的国家IP信息
     *
     * @param countryId
     * @return
     */
    public IpInfo getIpInfoFromCountryFile(String countryId) {
        Country c = countryMap.get(countryId);
        IpInfo ipinfo = new IpInfo();
        if (c == null) {
            logger.info("can not found the country info from local country file. countryId is : {}", countryId);
            return ipinfo;
        }
        ipinfo.setCountry(c.getChsName());
        return ipinfo;
    }

    /**
     * 根据startIp和hostNum取得endIp
     *
     * @param startIp
     * @param hostNum
     */
    private byte[] getEndIp(byte[] startIp, long hostNum) {
        int[] ipInt = new int[4];
        ipInt[0] = (int) (startIp[0] & 0xFF);
        ipInt[1] = startIp[1] & 0xFF;
        ipInt[2] = startIp[2] & 0xFF;
        ipInt[3] = startIp[3] & 0xFF;
        long ipLong = ipInt[0] * (1l << 24) + ipInt[1] * (1l << 16) + ipInt[2] * (1l << 8) + ipInt[3];
        ipLong += hostNum;
        byte[] endIp = new byte[4];
        endIp[3] = (byte) (ipLong % 256);
        ipLong >>= 8;
        endIp[2] = (byte) (ipLong % 256);
        ipLong >>= 8;
        endIp[1] = (byte) (ipLong % 256);
        ipLong >>= 8;
        endIp[0] = (byte) (ipLong % 256);
        return endIp;
    }

    /**
     * byte转换成string
     *
     * @param ip
     * @return
     */
    private String byteToIpString(byte[] ip) {
        int ip0 = ip[0] & 0xFF;
        int ip1 = ip[1] & 0xFF;
        int ip2 = ip[2] & 0xFF;
        int ip3 = ip[3] & 0xFF;
        return new StringBuilder()
                .append(ip0).append(".")
                .append(ip1).append(".")
                .append(ip2).append(".")
                .append(ip3).toString();
    }

    /**
     * 存储IP信息到IP库文件
     *
     * @throws IuniDAIpException
     */
    private void saveIpStore() throws IuniDAIpException {
        // ipstore大小为0，直接返回
        if (ipRangeList.size() == 0)
            return;
        String iplibFilePath;
        try {
            // iplibFilePath = IpUtils.class.getResource(ipUtils.iplibPath).getPath();
            iplibFilePath = System.getProperty("user.dir") + ipUtils.iplibPath;
        } catch (Exception e) {
            String errorStr = new StringBuilder("IpStore local apnicPath is not exsit:")
                    .append(ipUtils.iplibPath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        RandomAccessFile randomFile;
        try {
            randomFile = new RandomAccessFile(iplibFilePath, "rw");
        } catch (FileNotFoundException e) {
            String errorStr = new StringBuilder("IpStore get ip range error, file not found, file path is:")
                    .append(ipUtils.iplibPath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        // 存储头信息
        // first start ip
        int firstStartIp = HEAD_FIELD_LENGTH;
        try {
            randomFile.writeInt(firstStartIp);
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore save first start ip error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        // last start ip
        // int lastStartIpPos = HEAD_FIELD_LENGTH + (ipRangeList.size() - 1) * IP_REGION_FIELD_LENGTH;
        int lastStartIpPos = firstStartIp - IP_REGION_FIELD_LENGTH;
        try {
            randomFile.writeInt(lastStartIpPos);
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore save last start ip error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        // store size
        int storeSize = 0;
        try {
            randomFile.writeInt(storeSize);
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore save store size error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        // ip num
        long ipNum = 0;
        try {
            randomFile.writeLong(ipNum);
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore save ip number error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }

        int ipAreaPos = HEAD_FIELD_LENGTH;
        // int ipInfoAreaPos = lastStartIpPos + IP_REGION_FIELD_LENGTH;
        int ipInfoAreaPos = chinaIpRangeNum * minIpRangeSize;
        // 获取ip信息
        for (IpRange ipRange : ipRangeList) {
            try {
                ipNum += ipRange.getHostNum();
                byte[] startIp = ipRange.getStartIp();
                long n = ipRange.getHostNum() / minIpRangeSize;
                IpInfo lastIpInfo = new IpInfo();
                int lastIpInfoAreaPos = ipInfoAreaPos;
                for (int i = 0; i < n; i++) {
                    byte[] tmpStartIp = getEndIp(startIp, i * minIpRangeSize);
                    byte[] tmpEndIp = getEndIp(startIp, (i + 1) * minIpRangeSize);
                    IpInfo ipInfo;
                    if (COUNTRY_ID_CHINA.equals(ipRange.getCountryId())) {
                        ipInfo = getIpInfoFromTaobao(byteToIpString(tmpStartIp));
                    } else {
                        ipInfo = getIpInfoFromCountryFile(ipRange.getCountryId());
                    }
                    logger.info(ipInfo.toString());

                    // 如果IP地址信息同上一条记录不同，则写入文件
                    if (!ipInfo.equals(lastIpInfo)) {
                        // 写入startIp
                        randomFile.seek(ipAreaPos);
                        randomFile.write(tmpStartIp);
                        // 写入end ip位置
                        randomFile.writeInt(ipInfoAreaPos);
                        // 修改ipAreaPos位置
                        ipAreaPos += IP_REGION_FIELD_LENGTH;
                        // 写入endIp
                        randomFile.seek(ipInfoAreaPos);
                        randomFile.write(tmpEndIp);
                        // 写入IP地址详细信息
                        randomFile.writeUTF(ipInfo.getCountry());
                        randomFile.writeUTF(ipInfo.getArea());
                        randomFile.writeUTF(ipInfo.getRegion());
                        randomFile.writeUTF(ipInfo.getCity());
                        randomFile.writeUTF(ipInfo.getCounty());
                        randomFile.writeUTF(ipInfo.getIsp());
                        // 保存上一次位置
                        lastIpInfoAreaPos = ipInfoAreaPos;
                        // 保存上一次取得的ip信息
                        lastIpInfo = ipInfo;
                        ipInfoAreaPos = (int) randomFile.length();
                        // 更新lastStartIpPos
                        lastStartIpPos += IP_REGION_FIELD_LENGTH;
                        // 更新storeSize
                        storeSize += 1;
                    }
                    // 如果IP地址信息同上一条记录相同，则修改endip，但endIp和ipInfo的位置不变
                    else {
                        randomFile.seek(lastIpInfoAreaPos);
                        randomFile.write(tmpEndIp);
                    }
                }
            } catch (Exception e) {
                String errorStr = new StringBuilder("IpStore save ip info error. error msg: ")
                        .append(e.getMessage())
                        .toString();
                logger.error(errorStr);
                throw new IuniDAIpException(errorStr);
            }
        }

        // setBasicInfoForUpdate lastIpInfoAreaPos, storeSize, ipNum
        try {
            randomFile.seek(4);
            randomFile.writeInt(lastStartIpPos);
            randomFile.writeInt(storeSize);
            randomFile.writeLong(ipNum);
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore setBasicInfoForUpdate ip number error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }

        try {
            randomFile.close();
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore close file error: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    /**
     * 准备国家信息
     *
     * @throws IuniDAIpException
     */
    private void prepCountryInfo() throws IuniDAIpException {
        // String countryFilePath = IpStore.class.getResource(countryPath).getPath();
        String countryFilePath = System.getProperty("user.dir") + countryPath;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(countryFilePath)));
        } catch (FileNotFoundException e) {
            String errorStr = new StringBuilder(
                    "IpStore prepare country info error, file not found, file path is:")
                    .append(countryPath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        String info;
        String engRegex = "[^(A-Za-z)]";
        String chsRegex = "[^(\\u4e00-\\u9fa5)]";
        try {
            while ((info = reader.readLine()) != null) {
                String countryId = info.replaceAll(engRegex, "").toUpperCase();
                String chsName = info.replaceAll(chsRegex, "");
                countryMap.put(countryId, new Country(countryId, "", chsName));
            }
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore prepare country info error. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    /**
     * 准备库文件
     *
     * @throws IuniDAIpException
     */
    private void prepIpStoreFile() throws IuniDAIpException {
        // 清空旧文件
        // String filePath = System.getProperty("user.dir") + ipUtils.iplibPath;
        String filePath = null;
        try {
            File jarFile = new File(java.net.URLDecoder.decode(ipUtils.jarFilePath, "UTF-8"));
            File parent = jarFile.getParentFile();
            filePath = parent.getAbsolutePath() + ipUtils.iplibPath;
        } catch (UnsupportedEncodingException e) {
            String errorStr = new StringBuilder("IpUtils init failed, not found jar file path. jarFilePath is:")
                    .append(ipUtils.jarFilePath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        File file = new File(filePath);
        if (file.exists())
            file.delete();
        // 创建新文件
        try {
            file.createNewFile();
        } catch (IOException e) {
            String errorStr = new StringBuilder("IpStore prepare ip lib file error, setBasicInfoForCreate lib file failed")
                    .append(". filePath: ")
                    .append(filePath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    /**
     * 初始化IP库
     *
     * @throws IuniDAIpException
     */
    public void initIpStore() throws IuniDAIpException {
        // 准备库文件
        prepIpStoreFile();
        // 初始化国家信息
        prepCountryInfo();
        // 获取ip段
        getIpRange();
        // 存储IP信息
        saveIpStore();
    }

    public List<IpRange> initIpRange() throws IuniDAIpException {
        // 准备库文件
        prepIpStoreFile();
        // 初始化国家信息
        prepCountryInfo();
        // 获取ip段
        getIpRange();
        return this.ipRangeList;
    }

    /**
     * ipRange比较器
     *
     * @author Nicholas
     */
    private class IpComparator implements Comparator<IpRange> {
        public int compare(IpRange ipRange1, IpRange ipRange2) {
            byte[] ip1 = ipRange1.getStartIp();
            byte[] ip2 = ipRange2.getStartIp();
            return ipUtils.compareIP(ip1, ip2);
        }
    }

}