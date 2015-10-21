package com.iuni.data.utils;

import oracle.sql.TIMESTAMP;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ExcelUtils {

    // 定制日期格式
    private static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    private static final String STYLE_NAME_HEAD = "headStyle";
    private static final String STYLE_NAME_BODY = "bodyStyle";
    private static final String STYLE_NAME_DATE = "dateStyle";
    private static final String STYLE_NAME_NUMBER = "numberStyle";

    /**
     * 合并单元格后给合并后的单元格加边框
     *
     * @param region
     * @param cs
     * @param sheet
     */
    private static void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs, XSSFSheet sheet) {

        int toprowNum = region.getFirstRow();
        for (int i = toprowNum; i <= region.getLastRow(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                XSSFCell cell = row.getCell(j);// XSSFCellUtil.getCell(row,
                // (short) j);
                cell.setCellStyle(cs);
            }
        }
    }

    /**
     * 公共样式
     *
     * @param wb
     * @return
     */
    private static CellStyle genCommonStyle(SXSSFWorkbook wb) {
        // 创建单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        Font font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 设置表头的单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle genHeadStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = genCommonStyle(wb);
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    /**
     * 设置表体的单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle genBodyStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = genCommonStyle(wb);
        return cellStyle;
    }

    /**
     * 设置日期的单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle genDateStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = genCommonStyle(wb);
        // 设置日期格式
        cellStyle.setDataFormat(wb.createDataFormat().getFormat(DATE_FORMAT_SHORT));
        return cellStyle;
    }

    /**
     * 设置数字的单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle genNumberStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = genCommonStyle(wb);
        return cellStyle;
    }

    /**
     * 生成表格样式
     *
     * @param wb
     * @return
     */
    private static Map<String, CellStyle> genStyle(SXSSFWorkbook wb) {
        Map<String, CellStyle> styleMap = new HashMap<>();
        styleMap.put(STYLE_NAME_HEAD, genHeadStyle(wb));
        styleMap.put(STYLE_NAME_BODY, genBodyStyle(wb));
        styleMap.put(STYLE_NAME_DATE, genDateStyle(wb));
        styleMap.put(STYLE_NAME_NUMBER, genNumberStyle(wb));
        return styleMap;
    }

    /**
     * 设置表格数据
     *
     * @param cell
     * @param value
     * @param styleMap
     */
    private static void setCellData(Cell cell, Object value, Map<String, CellStyle> styleMap) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
            cell.setCellStyle(styleMap.get(STYLE_NAME_BODY));
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
            cell.setCellStyle(styleMap.get(STYLE_NAME_NUMBER));
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
            cell.setCellStyle(styleMap.get(STYLE_NAME_NUMBER));
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
            cell.setCellStyle(styleMap.get(STYLE_NAME_NUMBER));
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
            cell.setCellStyle(styleMap.get(STYLE_NAME_NUMBER));
        } else if (value instanceof Date || value instanceof java.sql.Date) {
            cell.setCellValue(((Date) value));
            cell.setCellStyle(styleMap.get(STYLE_NAME_DATE));
        } else if (value instanceof Timestamp) {
            cell.setCellValue(((Timestamp) value));
            cell.setCellStyle(styleMap.get(STYLE_NAME_DATE));
        } else {
            if (null != value) {
                cell.setCellValue(value.toString());
                cell.setCellStyle(styleMap.get(STYLE_NAME_BODY));
            }
        }
    }

    public static SXSSFWorkbook generateExcelWorkBook(Map<String, String> tableHeader, List<Map<String, Object>> tableData) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Map<String, CellStyle> styleMap = genStyle(wb);

        Set<Map.Entry<String, String>> tableHeaderEntrySet = tableHeader.entrySet();
        // 构建表头
        Row headRow = sheet.createRow(0);
        int i = 0;
        for (Map.Entry<String, String> entry : tableHeaderEntrySet) {
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(styleMap.get(STYLE_NAME_HEAD));
            cell.setCellValue(entry.getKey());
            i++;
        }

        // 构建表体数据
        if (tableData != null && tableData.size() > 0) {
            int j = 0;
            for (Map<String, Object> rowData : tableData) {
                Row bodyRow = sheet.createRow(j + 1);
                int k = 0;
                for (Map.Entry<String, String> entry : tableHeaderEntrySet) {
                    Cell cell = bodyRow.createCell(k);
                    setCellData(cell, rowData.get(entry.getValue()) == null ? "" : rowData.get(entry.getValue()), styleMap);
                    k++;
                }
                j++;
            }
        }

        // 设置列宽
        for (int cn = 0; cn < tableHeaderEntrySet.size(); cn++) {
            sheet.autoSizeColumn(cn, true);
            int width = sheet.getColumnWidth(cn) + 1024;
            int maxWidth = 10000;
            if (width > maxWidth)
                width = maxWidth;
            sheet.setColumnWidth(cn, width);
            cn++;
        }
        return wb;
    }

}
