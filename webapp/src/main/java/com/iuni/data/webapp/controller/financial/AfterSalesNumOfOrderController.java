package com.iuni.data.webapp.controller.financial;

import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderQueryDto;
import com.iuni.data.persist.model.financial.AfterSalesNumOfOrderTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.financial.AfterSalesService;
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
@Controller("afterSalesNumOfOrderControllerOfFinancial")
@RequestMapping("/financial/afterSalesNumOfOrder")
public class AfterSalesNumOfOrderController {

    private static final Logger logger = LoggerFactory.getLogger(AfterSalesNumOfOrderController.class);

    @Autowired
    private AfterSalesService afterSalesService;

    @RequestMapping
    public ModelAndView queryTable() {
        AfterSalesNumOfOrderQueryDto queryParam= new AfterSalesNumOfOrderQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") AfterSalesNumOfOrderQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.financial_after_sales_num_of_order.getPath());
        queryParam.parseDateRangeString();
        List<AfterSalesNumOfOrderTableDto> resultList = afterSalesService.selectAfterSalesNumOfOrder(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        AfterSalesNumOfOrderQueryDto queryParam = JsonUtils.fromJson(queryParamStr, AfterSalesNumOfOrderQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("订单售后次数统计报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<AfterSalesNumOfOrderTableDto> resultList = afterSalesService.selectAfterSalesNumOfOrder(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("订单售后次数", AfterSalesNumOfOrderTableDto.generateTableHeader(), AfterSalesNumOfOrderTableDto.generateTableData(resultList)));
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
