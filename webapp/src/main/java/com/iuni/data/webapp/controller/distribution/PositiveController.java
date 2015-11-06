package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.FreightQueryDto;
import com.iuni.data.persist.model.distribution.FreightTableDto;
import com.iuni.data.persist.model.distribution.PositiveQueryDto;
import com.iuni.data.persist.model.distribution.PositiveTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.distribution.PositiveService;
import com.iuni.data.webapp.service.distribution.ShippingService;
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
@Controller("positiveControllerOfDistribution")
@RequestMapping("/distribution/positive")
public class PositiveController {

    private static final Logger logger = LoggerFactory.getLogger(PositiveController.class);

    @Autowired
    private PositiveService positiveService;

    @RequestMapping
    public ModelAndView queryTable() {
        PositiveQueryDto queryParam= new PositiveQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") PositiveQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_positive.getPath());
        queryParam.parseDateRangeString();
        List<PositiveTableDto> resultList = positiveService.selectPositive(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        PositiveQueryDto queryParam = JsonUtils.fromJson(queryParamStr, PositiveQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("正向订单时效统计表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<PositiveTableDto> resultList = positiveService.selectPositive(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("正向订单时效统计", PositiveTableDto.generateTableHeader(), PositiveTableDto.generateTableData(resultList)));
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
