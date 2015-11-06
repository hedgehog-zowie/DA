package com.iuni.data.webapp.controller.financial;

import com.iuni.data.persist.mapper.financial.AfterSalesMapper;
import com.iuni.data.persist.model.financial.*;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.financial.AfterSalesService;
import com.iuni.data.webapp.service.financial.TransferService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller("notInWarrantyDetailsControllerOfFinancial")
@RequestMapping("/financial/notInWarrantyDetails")
public class NotInWarrantyDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(NotInWarrantyDetailsController.class);

    @Autowired
    private AfterSalesService afterSalesService;

    @RequestMapping
    public ModelAndView queryTable() {
        NotInWarrantyDetailsQueryDto queryParam= new NotInWarrantyDetailsQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") NotInWarrantyDetailsQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.financial_not_in_warranty_details.getPath());
        queryParam.parseDateRangeString();
        List<NotInWarrantyDetailsTableDto> resultList = afterSalesService.selectNotInWarrantyRepairs(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        NotInWarrantyDetailsQueryDto queryParam = JsonUtils.fromJson(queryParamStr, NotInWarrantyDetailsQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("非保修维修单明细报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<NotInWarrantyDetailsTableDto> resultList = afterSalesService.selectNotInWarrantyRepairs(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("非保修维修单明细", NotInWarrantyDetailsTableDto.generateTableHeader(), NotInWarrantyDetailsTableDto.generateTableData(resultList)));
            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(sheetDataList);
            wb.write(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.error("export to excel error. {}", e.getMessage(), e);
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                logger.error("export to excel error. {}", e.getMessage(), e);
            }
        }
        return null;
    }
}
