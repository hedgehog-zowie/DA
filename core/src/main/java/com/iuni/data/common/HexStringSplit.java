package com.iuni.data.common;

import com.google.common.base.Preconditions;
import java.math.BigInteger;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class HexStringSplit {
    public static final String DEFAULT_MIN_HEX = "00000000";
    public static final String DEFAULT_MAX_HEX = "FFFFFFFF";
    static String firstRow = "00000000";
    static BigInteger firstRowInt = BigInteger.ZERO;
    static String lastRow = "FFFFFFFF";
    static BigInteger lastRowInt = new BigInteger(lastRow, 16);
    static int rowComparisonLength = lastRow.length();

    public byte[] split(byte[] start, byte[] end) {
        BigInteger s = convertToBigInteger(start);
        BigInteger e = convertToBigInteger(end);
        Preconditions.checkArgument(!e.equals(BigInteger.ZERO));
        return convertToByte(split2(s, e));
    }

    public static byte[][] split(int n) {
        Preconditions.checkArgument(lastRowInt.compareTo(firstRowInt) > 0, "last row (%s) is configured less than first row (%s)", new Object[]{lastRow, firstRow});


        BigInteger range = lastRowInt.subtract(firstRowInt).add(BigInteger.ONE);

        Preconditions.checkState(range.compareTo(BigInteger.valueOf(n)) >= 0, "split granularity (%s) is greater than the range (%s)", new Object[]{Integer.valueOf(n), range});


        BigInteger[] splits = new BigInteger[n - 1];
        BigInteger sizeOfEachSplit = range.divide(BigInteger.valueOf(n));
        for (int i = 1; i < n; i++) {
            splits[(i - 1)] = firstRowInt.add(sizeOfEachSplit.multiply(BigInteger.valueOf(i)));
        }
        return convertToBytes(splits);
    }

    public byte[] firstRow() {
        return convertToByte(firstRowInt);
    }

    public byte[] lastRow() {
        return convertToByte(lastRowInt);
    }

    public void setFirstRow(String userInput) {
        firstRow = userInput;
        firstRowInt = new BigInteger(firstRow, 16);
    }

    public void setLastRow(String userInput) {
        lastRow = userInput;
        lastRowInt = new BigInteger(lastRow, 16);

        rowComparisonLength = lastRow.length();
    }

    public byte[] strToRow(String in) {
        return convertToByte(new BigInteger(in, 16));
    }

    public String rowToStr(byte[] row) {
        return Bytes.toStringBinary(row);
    }

    public String separator() {
        return " ";
    }

    public BigInteger split2(BigInteger a, BigInteger b) {
        return a.add(b).divide(BigInteger.valueOf(2L)).abs();
    }

    public static byte[][] convertToBytes(BigInteger[] bigIntegers) {
        byte[][] returnBytes = new byte[bigIntegers.length][];
        for (int i = 0; i < bigIntegers.length; i++) {
            returnBytes[i] = convertToByte(bigIntegers[i]);
        }
        return returnBytes;
    }

    public static byte[] convertToByte(BigInteger bigInteger, int pad) {
        String bigIntegerString = bigInteger.toString(16);
        bigIntegerString = StringUtils.leftPad(bigIntegerString, pad, '0');
        return Bytes.toBytes(bigIntegerString);
    }

    public static byte[] convertToByte(BigInteger bigInteger) {
        return convertToByte(bigInteger, rowComparisonLength);
    }

    public BigInteger convertToBigInteger(byte[] row) {
        return row.length > 0 ? new BigInteger(Bytes.toString(row), 16) : BigInteger.ZERO;
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + rowToStr(firstRow()) + "," + rowToStr(lastRow()) + "]";
    }
}
