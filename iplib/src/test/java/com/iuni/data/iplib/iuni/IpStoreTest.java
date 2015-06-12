package com.iuni.data.iplib.iuni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.iuni.data.exceptions.IuniDAIpException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IpStoreTest {

    private static IpStore ipStore;
    private static IpUtils ipUtils;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipStore = IpStore.getInstance();
        ipUtils = IpUtils.getInstance();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getEndIp() throws Exception {
        String startIpStr = "113.96.0.0";
        int hostNum = 1048576;
        byte[] startIp = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(startIpStr, ".");
        try {
            startIp[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            startIp[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            startIp[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            startIp[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

        int endIp0 = endIp[0] & 0xFF;
        int endIp1 = endIp[1] & 0xFF;
        int endIp2 = endIp[2] & 0xFF;
        int endIp3 = endIp[3] & 0xFF;

        System.out.println(endIp0 + "." + endIp1 + "." + endIp2 + "." + endIp3);
    }

    @Test
    public void testInitIpStore() throws IuniDAIpException {
        ipStore.initIpStore();
    }

    @Test
    public void testRead() throws IOException, IuniDAIpException {
        // ipRangeList.initIpStore();

        RandomAccessFile randomFile = new RandomAccessFile(IpStore.class
                .getResource(ipUtils.iplibPath).getPath(), "r");
        // 读头
        int firstStartIpPos = 0;
        randomFile.seek(0);
        firstStartIpPos = randomFile.readInt();
        int lastStartIpPos = 0;
        randomFile.seek(4);
        lastStartIpPos = randomFile.readInt();
        int storeSize = 0;
        randomFile.seek(8);
        storeSize = randomFile.readInt();
        long ipNum = 0;
        randomFile.seek(12);
        ipNum = randomFile.readLong();
        System.out.println(firstStartIpPos + " -- " + lastStartIpPos + " -- "
                + storeSize + " -- " + ipNum);

        FileWriter fileWriter = new FileWriter("ipStore.csv", true);
        // 读IP段 及 IP信息
        int pos = 20;
        for (int i = 0; i < storeSize; i++) {
            // IP段
            randomFile.seek(pos);
            byte[] startip = new byte[4];
            randomFile.readFully(startip);
            int startip0 = startip[0] & 0xFF;
            int startip1 = startip[1] & 0xFF;
            int startip2 = startip[2] & 0xFF;
            int startip3 = startip[3] & 0xFF;
            randomFile.seek(pos += 4);
            int endIpPos = randomFile.readInt();
            pos += 4;

            // 信息
            randomFile.seek(endIpPos);
            byte[] endip = new byte[4];
            randomFile.readFully(endip);
            int endip0 = endip[0] & 0xFF;
            int endip1 = endip[1] & 0xFF;
            int endip2 = endip[2] & 0xFF;
            int endip3 = endip[3] & 0xFF;
            randomFile.seek(endIpPos + 4);
            try {
                String country = randomFile.readUTF();
                String area = randomFile.readUTF();
                String region = randomFile.readUTF();
                String city = randomFile.readUTF();
                String county = randomFile.readUTF();
                String isp = randomFile.readUTF();
                String str = startip0 + "." + startip1 + "." + startip2 + "." + startip3 + ","
                        + endip0 + "." + endip1 + "." + endip2 + "." + endip3 + ","
                        + country + "," + area + "," + region + "," + city + "," + county + "," + isp + "\n";
                // System.out.println(str);
                fileWriter.write(str);
                fileWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        randomFile.close();
        fileWriter.close();
    }

    @Test
    public void test() throws IOException {
        String filename = "asdf.dat";
        File file = new File(filename);
        if (file.exists())
            file.delete();
        file.createNewFile();
        RandomAccessFile randomFile = new RandomAccessFile(filename, "rw");
        randomFile.seek(0);
        randomFile.write(123);
        System.out.println("randomFile.length():" + randomFile.length());
        randomFile.seek(1);
        randomFile.write(222);
        System.out.println("randomFile.length():" + randomFile.length());
        randomFile.seek(2);
        randomFile.write(245);
        System.out.println("randomFile.length():" + randomFile.length());
        randomFile.seek(3);
        randomFile.write(230);
        System.out.println("randomFile.length():" + randomFile.length());
        byte[] b = new byte[4];
        randomFile.seek(0);
        randomFile.readFully(b);
        int ip0 = b[0] & 0xFF;
        int ip1 = b[1] & 0xFF;
        int ip2 = b[2] & 0xFF;
        int ip3 = b[3] & 0xFF;
        System.out.println(ip0 + "." + ip1 + "." + ip2 + "." + ip3);

        long pos = randomFile.length();
        randomFile.seek(pos);
        randomFile.writeUTF("asdf1");
        randomFile.writeUTF("asdf2");
        randomFile.writeUTF("asdf3");
        randomFile.seek(pos);
        System.out.println(randomFile.readUTF());
        System.out.println(randomFile.readUTF());
        System.out.println(randomFile.readUTF());

        randomFile.close();
    }

    @Test
    public void getAllCountry() throws IOException {
        String countryFilePath = IpStore.class.getResource(ipStore.countryPath)
                .getPath();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(countryFilePath)));
        String info;
        String engRegex = "[^(A-Za-z)]";
        String chsRegex = "[^(\\u4e00-\\u9fa5)]";
        SortedMap<String, String> cmap = new TreeMap<String, String>();
        while ((info = reader.readLine()) != null) {
            String countryId = info.replaceAll(engRegex, "");
            String chsName = info.replaceAll(chsRegex, "");
            cmap.put(countryId.toUpperCase(), chsName);
        }

        List<String> fileList = new ArrayList<String>();
        String afrinicFilePath = IpStore.class.getResource(ipStore.afrinicPath).getPath();
        String apnicFilePath = IpStore.class.getResource(ipStore.apnicPath).getPath();
        String arinFilePath = IpStore.class.getResource(ipStore.arinPath).getPath();
        String ripenccFilePath = IpStore.class.getResource(ipStore.ripenccPath).getPath();
        String lacnicFilePath = IpStore.class.getResource(ipStore.lacnicPath).getPath();

        fileList.add(afrinicFilePath);
        fileList.add(apnicFilePath);
        fileList.add(arinFilePath);
        fileList.add(ripenccFilePath);
        fileList.add(lacnicFilePath);

        SortedSet<String> cset = new TreeSet<String>();
        for (String filePath : fileList) {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));
            while ((info = reader.readLine()) != null) {
                if (info.startsWith("afrini|") || info.startsWith("apnic|")
                        || info.startsWith("arin|")
                        || info.startsWith("lacnic|")
                        || info.startsWith("ripencc|")) {
                    String[] str = info.split("[|]");
                    if (!"".equals(str[1])
                            && str[2].equals("ipv4")
                            && str[3]
                            .matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                        cset.add(str[1]);
                    }
                }
            }
        }

        for (String cid : cset) {
            if (cmap.get(cid) == null)
                System.out.println("error------------------------" + cid);
            else
                System.out.println(cid + " -- " + cmap.get(cid));
        }
    }

    @Test
    public void testSaveIpRange() throws IOException, IuniDAIpException {
        List<IpRange> ipRangeList = ipStore.initIpRange();
        // save all ip range
        String filepath = System.getProperty("user.dir") + "/ipRange.csv";
        File file = new File(filepath);
        if (file.exists())
            file.delete();
        FileWriter fileWriter = new FileWriter(filepath, true);
        for (IpRange ipRange : ipRangeList) {
            String str = new StringBuilder()
                    .append(ipRange.getStartIpStr())
                    .append(",")
                    .append(ipRange.getHostNum())
                    .append(",")
                    .append(ipRange.getCountryId())
                    .append("\n")
                    .toString();
            fileWriter.write(str);
            fileWriter.flush();
        }
        fileWriter.close();
    }

}
