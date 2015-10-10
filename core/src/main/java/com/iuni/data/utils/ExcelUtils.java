package com.iuni.data.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ExcelUtils {

    /**
     * 合并单元格后给合并后的单元格加边框
     *
     * @param region
     * @param cs
     */
    public static void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs, XSSFSheet sheet) {

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
     * 设置表头的单元格样式
     *
     * @return
     */
    public static CellStyle getHeadStyle(SXSSFWorkbook wb) {
        // 创建单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
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
     * 设置表体的单元格样式
     *
     * @return
     */
    public static CellStyle getBodyStyle(SXSSFWorkbook wb) {
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

    public static SXSSFWorkbook generateExcelWorkBook(Map<String, String> tableHeaders, List<Map<String, String>> tableDatas) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();

        CellStyle headStyle = getHeadStyle(wb);
        CellStyle bodyStyle = getBodyStyle(wb);

        Set<Map.Entry<String, String>> tableHeaderEntrySet = tableHeaders.entrySet();

        // 构建表头
        Row headRow = sheet.createRow(0);
        int i = 0;
        for (Map.Entry<String, String> entry : tableHeaderEntrySet) {
            Cell cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(entry.getKey());
            i++;
        }

        // 构建表体数据
        if (tableDatas != null && tableDatas.size() > 0) {
            int j = 0;
            for (Map<String, String> rowData : tableDatas) {
                Row bodyRow = sheet.createRow(j + 1);
                int k = 0;
                for (Map.Entry<String, String> entry : tableHeaderEntrySet) {
                    Cell cell = bodyRow.createCell(k);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(rowData.get(entry.getValue()) == null ? "" : rowData.get(entry.getValue()));
                    k++;
                }
                j++;
            }
        }

        // 设置列宽
        int cn = 0;
        for (Map.Entry<String, String> entry : tableHeaderEntrySet) {
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
