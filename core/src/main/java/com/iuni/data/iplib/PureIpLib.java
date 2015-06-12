package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.iuni.data.Context;
import com.iuni.data.IpLib;
import com.iuni.data.conf.Configurable;
import com.iuni.data.exceptions.IuniDAIpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.util.Hashtable;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PureIpLib extends AbstractIpLib implements Configurable, IpLib {

    private static final Logger logger = LoggerFactory.getLogger(PureIpLib.class);

    private String libFilePath;
    private Hashtable<String, IPLocation> ipCache;

    // 一些固定常量，比如记录长度等等
    private static final int IP_RECORD_LENGTH = 7;
    private static final byte AREA_FOLLOWED = 0x01;
    private static final byte NO_AREA = 0x2;
    // 起始地区的开始和结束的绝对偏移
    private long ipBegin, ipEnd;
    private MappedByteBuffer mbb;
    private byte[] buf;
    private byte[] b4;
    private byte[] b3;
    private IPLocation loc;

    private RandomAccessFile ipFile;

    public PureIpLib() {
    }

    public String getLibFilePath() {
        return libFilePath;
    }

    public void setLibFilePath(String libFilePath) {
        this.libFilePath = libFilePath;
    }

    @Override
    public void configure(Context context) {
        String libFilePath = context.getString("lib");
        Preconditions.checkState(libFilePath != null, "The parameter lib must be specified");
        File file = new File(libFilePath);
        try {
            ipFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            String errorStr = new StringBuilder("PureIpLib configure failed, not found ip lib file. iplibPath is:")
                    .append(libFilePath)
                    .append(". error msg: ")
                    .append(e.getMessage())
                    .toString();
            logger.error(errorStr);
            throw new IuniDAIpException(errorStr);
        }
        ipCache = new Hashtable<String, IPLocation>();
        loc = new IPLocation();
        buf = new byte[100];
        b4 = new byte[4];
        b3 = new byte[3];
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
        ipInfo.setIp(ipStr);
        // 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
        if (ipCache.containsKey(ipStr)) {
            IPLocation loc = ipCache.get(ipStr);
            ipInfo.setAddress(loc.getLocation());
        } else {
            IPLocation loc = getIPLocation(getIpByteArrayFromString(ipStr));
            ipCache.put(ipStr, loc.getCopy());
            ipInfo.setAddress(loc.getLocation());
        }
        return ipInfo;
    }

    /**
     * 根据ip搜索ip信息文件，得到IPLocation结构，所搜索的ip参数从类成员ip中得到
     *
     * @param ipByteArrayFromString 要查询的IP
     * @return IPLocation结构
     */
    private IPLocation getIPLocation(byte[] ipByteArrayFromString) {
        IPLocation info = null;
        long offset = locateIP(ipByteArrayFromString);
        if (offset != -1)
            info = getIPLocation(offset);
        if (info == null) {
            info = new IPLocation();
            info.area = "未知地理区域";
            info.location = "未知位置或服务";
        }
        return info;
    }

    /**
     * 给定一个IP地理区域记录的偏移，返回一个IPLocation结构
     *
     * @param offset
     * @return
     */
    private IPLocation getIPLocation(long offset) {
        try {
            // 跳过4字节ip
            ipFile.seek(offset + 4);
            // 读取第一个字节判断是否标志字节
            byte b = ipFile.readByte();
            if (b == AREA_FOLLOWED) {
                // 读取国家偏移
                long countryOffset = readLong3();
                // 跳转至偏移处
                ipFile.seek(countryOffset);
                // 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
                b = ipFile.readByte();
                if (b == NO_AREA) {
                    loc.area = readString(readLong3());
                    ipFile.seek(countryOffset + 4);
                } else
                    loc.area = readString(countryOffset);
                // 读取地区标志
                loc.location = readArea(ipFile.getFilePointer());
            } else if (b == NO_AREA) {
                loc.area = readString(readLong3());
                loc.location = readArea(offset + 8);
            } else {
                loc.area = readString(ipFile.getFilePointer() - 1);
                loc.location = readArea(ipFile.getFilePointer());
            }
            return loc;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 这个方法将根据ip的内容，定位到包含这个ip国家地区的记录处，返回一个绝对偏移 方法使用二分法查找。
     *
     * @param ip 要查询的IP
     * @return 如果找到了，返回结束IP的偏移，如果没有找到，返回-1
     */
    private long locateIP(byte[] ip) {
        long m = 0;
        int r;
        // 比较第一个ip项
        readIP(ipBegin, b4);
        r = compareIP(ip, b4);
        if (r == 0)
            return ipBegin;
        else if (r < 0)
            return -1;
        // 开始二分搜索
        for (long i = ipBegin, j = ipEnd; i < j; ) {
            m = getMiddleOffset(i, j);
            readIP(m, b4);
            r = compareIP(ip, b4);
            // log.debug(Utils.getIpStringFromBytes(b));
            if (r > 0)
                i = m;
            else if (r < 0) {
                if (m == j) {
                    j -= IP_RECORD_LENGTH;
                    m = j;
                } else
                    j = m;
            } else
                return readLong3(m + 4);
        }
        // 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
        // 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
        m = readLong3(m + 4);
        readIP(m, b4);
        r = compareIP(ip, b4);
        if (r <= 0)
            return m;
        else
            return -1;
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
     * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
     * 文件中是little-endian形式，将会进行转换
     *
     * @param offset
     * @param ip
     */
    private void readIP(long offset, byte[] ip) {
        try {
            ipFile.seek(offset);
            ipFile.readFully(ip);
            byte temp = ip[0];
            ip[0] = ip[3];
            ip[3] = temp;
            temp = ip[1];
            ip[1] = ip[2];
            ip[2] = temp;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 从ip的字符串形式得到字节数组形式
     *
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    /**
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * 从内存映射文件的当前位置开始的3个字节读取一个int
     *
     * @return
     */
    private int readInt3() {
        return mbb.getInt() & 0x00FFFFFF;
    }

    /**
     * 从当前位置读取3个字节转换成long
     *
     * @return
     */
    private long readLong3() {
        long ret = 0;
        try {
            ipFile.readFully(b3);
            ret |= (b3[0] & 0xFF);
            ret |= ((b3[1] << 8) & 0xFF00);
            ret |= ((b3[2] << 16) & 0xFF0000);
            return ret;
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * 从offset位置读取3个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
     *
     * @param offset
     * @return 读取的long值，返回-1表示读取文件失败
     */
    private long readLong3(long offset) {
        long ret = 0;
        try {
            ipFile.seek(offset);
            ipFile.readFully(b3);
            ret |= (b3[0] & 0xFF);
            ret |= ((b3[1] << 8) & 0xFF00);
            ret |= ((b3[2] << 16) & 0xFF0000);
            return ret;
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * 从offset偏移处读取一个以0结束的字符串
     *
     * @param offset
     * @return 读取的字符串，出错返回空字符串
     */
    private String readString(long offset) {
        try {
            ipFile.seek(offset);
            int i;
            for (i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] = ipFile
                    .readByte())
                ;
            if (i != 0)
                return getString(buf, 0, i, "GBK");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * 根据某种编码方式将字节数组转换成字符串
     *
     * @param b        字节数组
     * @param offset   要转换的起始位置
     * @param len      要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len,
                                   String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }

    /**
     * @param offset
     * @return
     */
    private String readArea(int offset) {
        mbb.position(offset);
        byte b = mbb.get();
        if (b == 0x01 || b == 0x02) {
            int areaOffset = readInt3();
            if (areaOffset == 0)
                return "未知地区";
            else
                return readString(areaOffset);
        } else
            return readString(offset);
    }

    /**
     * 从offset偏移开始解析后面的字节，读出一个地区名
     *
     * @param offset
     * @return 地区名字符串
     * @throws IOException
     */
    private String readArea(long offset) throws IOException {
        ipFile.seek(offset);
        byte b = ipFile.readByte();
        if (b == 0x01 || b == 0x02) {
            long areaOffset = readLong3(offset + 1);
            if (areaOffset == 0)
                return "未知地区";
            else
                return readString(areaOffset);
        } else
            return readString(offset);
    }

    /**
     * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
     *
     * @param ip      要查询的IP
     * @param beginIp 和被查询IP相比较的IP
     * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
     */
    private int compareIP(byte[] ip, byte[] beginIp) {
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

    /**
     * 用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
     */
    private class IPLocation {
        public String area;
        public String location;

        public IPLocation() {
            area = location = "";
        }

        public IPLocation getCopy() {
            IPLocation ret = new IPLocation();
            ret.area = area;
            ret.location = location;
            return ret;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

}
