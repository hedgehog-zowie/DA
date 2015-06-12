package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.exceptions.IuniDAIpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class IuniIpLib extends AbstractIpLib {

    private static final Logger logger = LoggerFactory.getLogger(IuniIpLib.class);
    private final int IP_RECORD_LENGTH = 8;

    private RandomAccessFile ipFile;
    private int ipBegin;
    private int ipEnd;

    public IuniIpLib() {
        super();
    }

    @Override
    public void configure(Context context) {
        String libFilePath = context.getString("lib");
        Preconditions.checkState(libFilePath != null, "The parameter lib must be specified");
        File file = new File(libFilePath);
        try {
            ipFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            String errorStr = new StringBuilder("IuniIpLib configure failed, not found ip lib file. iplibPath is:")
                    .append(libFilePath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        // check ip file
        try {
            ipBegin = ipFile.readInt();
            ipEnd = ipFile.readInt();
        } catch (IOException e) {
            String errorStr = new StringBuilder("IuniIpLib config failed, read head info failed. error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    @Override
    public void start() {
        logger.info("IuniIpLib starting");
        super.start();
    }

    @Override
    public void stop() {
        logger.info("IuniIpLib stop");
        super.stop();
    }

    @Override
    @Deprecated
    public void getIpInfo(IpInfo ipInfo) {
        logger.info("get ip info of {}", ipInfo.getIp());
        ipInfo.setIp(ipInfo.getIp() + "getIpInfo");
    }

    @Override
    public IpInfo getIpInfo(String ipStr) {
        logger.debug("get ip info of {}", ipStr);
        ipStr = ipStr.trim();
        IpInfo ipInfo = new IpInfo();
        ipInfo.setIp(ipStr);
        long endIpPos = -1;
        try {
            endIpPos = locateIP(getIpByteArrayFromString(ipStr));
        }catch (IuniDAIpException e){
            String errorStr = new StringBuilder("IuniIpLib getIpInfo failed, ip: ")
                    .append(ipStr)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            return ipInfo;
        }

        if (endIpPos == -1) {
            logger.error("not found ip in local ip lib file, ip: {}", ipStr);
            return ipInfo;
        }

        try {
            ipFile.seek(endIpPos + 4);
            String country = ipFile.readUTF();
            String area = ipFile.readUTF();
            String region = ipFile.readUTF();
            String city = ipFile.readUTF();
            String county = ipFile.readUTF();
            String isp = ipFile.readUTF();
            logger.debug("{} -- {} -- {} -- {} -- {} -- {} -- {}",
                    ipStr, country, area, region, city, county, isp);
            ipInfo.setCountry(country);
            ipInfo.setArea(area);
            ipInfo.setRegion(region);
            ipInfo.setCity(city);
            ipInfo.setCounty(county);
            ipInfo.setIsp(isp);
            return ipInfo;
        } catch (IOException e) {
            String errorStr = new StringBuilder("IuniIpLib getIpInfo failed, ip: ")
                    .append(ipStr)
                    .append(". endIpPos is: ")
                    .append(endIpPos)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            return ipInfo;
        }
    }

    /**
     * 从ip的字符串形式得到字节数组形式
     *
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     * @throws IuniDAIpException
     */
    public byte[] getIpByteArrayFromString(String ip) throws IuniDAIpException {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            String errorStr = new StringBuilder("IuniIpLib trans ip string to byte error, error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        return ret;
    }

    /**
     * 定位IP所属IP段
     *
     * @param ip
     * @return
     * @throws IuniDAIpException
     */
    private long locateIP(byte[] ip) throws IuniDAIpException {
        long m = 0;
        int r;
        byte[] rip = new byte[4];
        // 比较第一个ip项
        readIP(ipBegin, rip);
        r = compareIP(ip, rip);
        if (r == 0)
            return ipBegin;
        else if (r < 0)
            return -1;
        // 开始二分搜索
        for (long i = ipBegin, j = ipEnd; i < j; ) {
            m = getMiddleOffset(i, j);
            readIP(m, rip);
            r = compareIP(ip, rip);
            if (r > 0)
                i = m;
            else if (r < 0) {
                if (m == j) {
                    j -= IP_RECORD_LENGTH;
                    m = j;
                } else
                    j = m;
            } else
                return getEndIpPos(m + 4);
        }
        // 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
        // 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
        m = getEndIpPos(m + 4);
        readIP(m, rip);
        r = compareIP(ip, rip);
        if (r <= 0)
            return m;
        else
            return -1;
    }

    /**
     * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
     * 文件中是little-endian形式，将会进行转换
     *
     * @param offset
     * @param ip
     * @throws IuniDAIpException
     */
    private void readIP(long offset, byte[] ip) throws IuniDAIpException {
        try {
            ipFile.seek(offset);
            ipFile.readFully(ip);
        } catch (IOException e) {
            String errorStr = new StringBuilder(
                    "IuniIpLib readip from file error, offset is:")
                    .append(offset).append(". error msg: ")
                    .append(e.getMessage()).toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
    }

    /**
     * 得到begin偏移和end偏移中间位置记录的偏移
     *
     * @param begin
     * @param end
     * @return
     */
    private long getMiddleOffset(long begin, long end) {
        long records = (end - begin) / IP_RECORD_LENGTH;
        records >>= 1;
        if (records == 0)
            records = 1;
        return begin + records * IP_RECORD_LENGTH;
    }

    /**
     * 从offset位置读取endip偏移量
     *
     * @param offset
     * @return 读取的long值，返回-1表示读取文件失败
     * @throws IuniDAIpException
     */
    private long getEndIpPos(long offset) {
        try {
            ipFile.seek(offset);
            return ipFile.readInt();
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
     *
     * @param ip      要查询的IP
     * @param beginIp 和被查询IP相比较的IP
     * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
     */
    public int compareIP(byte[] ip, byte[] beginIp) {
        for (int i = 0; i < 4; i++) {
            int r = compareByte(ip[i], beginIp[i]);
            if (r != 0)
                return r;
        }
        return 0;
    }

    /**
     * 把两个byte当作无符号数进行比较
     *
     * @param b1
     * @param b2
     * @return 若b1大于b2则返回1，相等返回0，小于返回-1
     */
    private int compareByte(byte b1, byte b2) {
        if ((b1 & 0xFF) > (b2 & 0xFF)) // 比较是否大于
            return 1;
        else if ((b1 ^ b2) == 0)// 判断是否相等
            return 0;
        else
            return -1;
    }
}
