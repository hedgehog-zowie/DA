package com.iuni.data.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.iuni.data.common.TType;
import com.iuni.data.exceptions.IuniDADateFormatException;

public class DateUtils {

    private static final String SIMPLE_DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String SIMPLE_DATE_FORMAT_OF_DAY = "yyyyMMdd";
    private static final String LOG_DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
    private static final String LOG_DATE_FORMAT_MINUTE = "dd/MMM/yyyy:HH:mm";
    private static final String LOG_DATE_FORMAT_HOUR = "dd/MMM/yyyy:HH";
    private static final String LOG_DATE_FORMAT_DAY = "dd/MMM/yyyy";
    private static final String PAGE_DATE_FORMAT = "yyyy-MM-dd";

    public static Date simpleDateStrToDate(String dateStr, String formatStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat simpleSdf = new SimpleDateFormat(formatStr, Locale.US);
            return simpleSdf.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "simple date string format to date error. log date string is [" + dateStr
                    + "], format string is [" + formatStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date simpleDateStrToDate(String dateStr) throws IuniDADateFormatException {
        return simpleDateStrToDate(dateStr, SIMPLE_DATE_FORMAT);
    }

    public static String dateToSimpleDateStr(Date date, String formatStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat simpleSdf = new SimpleDateFormat(formatStr, Locale.US);
            return simpleSdf.format(date);
        } catch (Exception e) {
            String errorStr = "date format to simple date str error. log date is [" + date + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static String dateToSimpleDateStr(Date date) throws IuniDADateFormatException {
        return dateToSimpleDateStr(date, SIMPLE_DATE_FORMAT);
    }

    public static String dateToSimpleDateStrOfDay(Date date) throws IuniDADateFormatException {
        try {
            SimpleDateFormat simpleSdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT_OF_DAY, Locale.US);
            return simpleSdf.format(date);
        } catch (Exception e) {
            String errorStr = "date format to simple date str of day error. log date is [" + date + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date logDateStrToDate(String dateStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat logDateSdf = new SimpleDateFormat(LOG_DATE_FORMAT, Locale.US);
            return logDateSdf.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "log date string format to date error. log date string is [" + dateStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date logDateStrToDate_minute(String dateStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat logDateSdf_minute = new SimpleDateFormat(LOG_DATE_FORMAT_MINUTE, Locale.US);
            return logDateSdf_minute.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "log date string format to date error. log date string is [" + dateStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date logDateStrToDate_hour(String dateStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat logDateSdf_hour = new SimpleDateFormat(LOG_DATE_FORMAT_HOUR, Locale.US);
            return logDateSdf_hour.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "log date format to date error. log date string is [" + dateStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date logDateStrToDate_day(String dateStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat logDateSdf_day = new SimpleDateFormat(LOG_DATE_FORMAT_DAY, Locale.US);
            return logDateSdf_day.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "log date format to date error. log date string is [" + dateStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date pageDateStrToDate(String dateStr) throws IuniDADateFormatException {
        try {
            SimpleDateFormat logDateSdf = new SimpleDateFormat(PAGE_DATE_FORMAT, Locale.US);
            return logDateSdf.parse(dateStr.trim());
        } catch (Exception e) {
            String errorStr = "log date page format to date error. log date string is [" + dateStr + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static String dateToPageDateStr(Date date) throws IuniDADateFormatException {
        try {
            SimpleDateFormat simpleSdf = new SimpleDateFormat(PAGE_DATE_FORMAT, Locale.US);
            return simpleSdf.format(date);
        } catch (Exception e) {
            String errorStr = "date format to page date str error. log date is [" + date + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date computeStartDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    public static Date computeEndDate(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    public static Date computeStartHour(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR, amount);
        return calendar.getTime();
    }

    public static Date millTimestampToDate(long millTimestamp) {
        return new Date(millTimestamp);
    }

    /**
     * generate a map of startDate and endDate
     * startDate is key, endDate is value
     *
     * @param startDate
     * @param endDate
     * @param tType
     * @return
     */
    public static Map<Date, Date> parseTimeRange(Date startDate, Date endDate, TType tType) {
        Map<Date, Date> timeRangeMap = new LinkedHashMap<>();
        Date curDate = startDate;
        Calendar calendar = Calendar.getInstance();
        while (curDate.getTime() < endDate.getTime()) {
            Date keyTime = curDate;
            Date valueTime;
            calendar.setTime(curDate);
            switch (tType) {
                case YEAR:
                    calendar.set(Calendar.MONTH, Calendar.JANUARY);
                    calendar.set(Calendar.DAY_OF_YEAR, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.YEAR, 1);
                    break;
                case MONTH:
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case DAY:
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.DATE, 1);
                    break;
                case HOUR:
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.HOUR, 1);
                    break;
                case MINUTE:
                    calendar.set(Calendar.SECOND, 0);
                    calendar.add(Calendar.MINUTE, 1);
                    break;
            }
            curDate = calendar.getTime();
            valueTime = endDate.getTime() < curDate.getTime() ? endDate : curDate;
            timeRangeMap.put(keyTime, valueTime);
        }
        return timeRangeMap;
    }

    public static Map<Date, Date> parseTimeRange(String startTime, String endTime, TType tType) {
        Date startDate = DateUtils.simpleDateStrToDate(startTime);
        Date endDate = DateUtils.simpleDateStrToDate(endTime);
        Map<Date, Date> timeRangeMap = parseTimeRange(startDate, endDate, tType);
        return timeRangeMap;
    }

    public static Date transDateByTType(Date date, TType tType) throws IuniDADateFormatException {
        String formatString = generateDateFormat(tType);
        SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.US);
        String dateStr = sdf.format(date);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            String errorStr = "transDateByTType error. log date is [" + date + "], tType is [" + tType + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    public static Date transDateByTType(String dateStr, TType tType) throws IuniDADateFormatException {
        String formatString = generateDateFormat(tType);
        SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.US);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            String errorStr = "transDateByTType error. log date is [" + dateStr + "], tType is [" + tType + "]. error msg : " + e.getMessage();
            throw new IuniDADateFormatException(errorStr);
        }
    }

    private static String generateDateFormat(TType tType) {
        String formatString = "";
        if (tType.getValue() >= TType.YEAR.getValue())
            formatString += TType.YEAR.getPattern();
        if (tType.getValue() >= TType.MONTH.getValue())
            formatString += TType.MONTH.getPattern();
        if (tType.getValue() >= TType.DAY.getValue())
            formatString += TType.DAY.getPattern();
        if (tType.getValue() >= TType.MINUTE.getValue())
            formatString += TType.MINUTE.getPattern();
        if (tType.getValue() >= TType.HOUR.getValue())
            formatString += TType.HOUR.getPattern();
        if (tType.getValue() >= TType.SECOND.getValue())
            formatString += TType.SECOND.getPattern();
        return formatString;
    }


}
