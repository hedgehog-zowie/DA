package com.iuni.data.webapp.controller.financial;

import com.iuni.data.persist.model.financial.RebatesDetailsTableDto;
import com.iuni.data.persist.model.financial.RefundDetailsQueryDto;
import com.iuni.data.persist.model.financial.RefundDetailsTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.financial.ReturnGoodsService;
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
@Controller("refundDetailsControllerOfFinancial")
@RequestMapping("/financial/refundDetails")
public class RefundDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(RefundDetailsController.class);

    @Autowired
    private ReturnGoodsService returnGoodsService;

    @RequestMapping
    public ModelAndView queryTable() {
        RefundDetailsQueryDto queryParam= new RefundDetailsQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") RefundDetailsQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.financial_refund_details.getPath());
        queryParam.parseDateRangeString();
        List<RefundDetailsTableDto> resultList = returnGoodsService.selectRefundDetails(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        RefundDetailsQueryDto queryParam = JsonUtils.fromJson(queryParamStr, RefundDetailsQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("订单退款明细报表(" + queryParam.getDateRangeString() + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

            queryParam.parseDateRangeString();
            List<RefundDetailsTableDto> resultList = returnGoodsService.selectRefundDetails(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("订单退款明细", RefundDetailsTableDto.generateTableHeader(), RefundDetailsTableDto.generateTableData(resultList)));
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
