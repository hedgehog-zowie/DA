package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.ReverseSignOfRepairTableDto;
import com.iuni.data.persist.model.distribution.StockByChannelQueryDto;
import com.iuni.data.persist.model.distribution.StockByChannelTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.distribution.StockService;
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
@Controller("stockByChannelControllerOfDistribution")
@RequestMapping("/distribution/stockByChannel")
public class StockByChannelController {

    private static final Logger logger = LoggerFactory.getLogger(StockByChannelController.class);

    @Autowired
    private StockService stockService;

    @RequestMapping
    public ModelAndView queryTable() {
        StockByChannelQueryDto queryParam= new StockByChannelQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") StockByChannelQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_stock_by_channel.getPath());
        queryParam.parseDateRangeString();
        List<StockByChannelTableDto> resultList = stockService.selectStockByChannel(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        StockByChannelQueryDto queryParam = JsonUtils.fromJson(queryParamStr, StockByChannelQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("各渠道进退换数量汇总报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<StockByChannelTableDto> resultList = stockService.selectStockByChannel(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("各渠道进退换数量汇总", StockByChannelTableDto.generateTableHeader(), StockByChannelTableDto.generateTableData(resultList)));
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
