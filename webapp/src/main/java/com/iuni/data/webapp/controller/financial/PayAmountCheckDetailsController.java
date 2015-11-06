package com.iuni.data.webapp.controller.financial;

import com.iuni.data.persist.model.financial.NotInWarrantyDetailsTableDto;
import com.iuni.data.persist.model.financial.PayAmountCheckDetailsQueryDto;
import com.iuni.data.persist.model.financial.PayAmountCheckDetailsTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.financial.SalesOrderService;
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
@Controller("payAmountCheckDetailsControllerOfFinancial")
@RequestMapping("/financial/payAmountCheckDetails")
public class PayAmountCheckDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(PayAmountCheckDetailsController.class);

    @Autowired
    private SalesOrderService salesOrderService;

    @RequestMapping
    public ModelAndView queryTable() {
        PayAmountCheckDetailsQueryDto queryParam= new PayAmountCheckDetailsQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") PayAmountCheckDetailsQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.financial_pay_amount_check_details.getPath());
        queryParam.parseDateRangeString();
        List<PayAmountCheckDetailsTableDto> resultList = salesOrderService.selectPayAmountCheck(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        PayAmountCheckDetailsQueryDto queryParam = JsonUtils.fromJson(queryParamStr, PayAmountCheckDetailsQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("收款发货发票金额核对明细报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<PayAmountCheckDetailsTableDto> resultList = salesOrderService.selectPayAmountCheck(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("收款发货发票金额核对明细", PayAmountCheckDetailsTableDto.generateTableHeader(), PayAmountCheckDetailsTableDto.generateTableData(resultList)));
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
