package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.StockBySourceQueryDto;
import com.iuni.data.persist.model.distribution.StockBySourceTableDto;
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
@Controller("stockBySourceControllerOfDistribution")
@RequestMapping("/distribution/stockBySource")
public class StockBySourceController {

    private static final Logger logger = LoggerFactory.getLogger(StockBySourceController.class);

    @Autowired
    private StockService stockService;

    @RequestMapping
    public ModelAndView queryTable() {
        StockBySourceQueryDto queryParam= new StockBySourceQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") StockBySourceQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_stock_by_source.getPath());
        queryParam.parseDateRangeString();
        List<StockBySourceTableDto> resultListOfDay = stockService.selectStockBySourceOfDay(queryParam);
        List<StockBySourceTableDto> resultListOfRange = stockService.selectStockBySourceOfRange(queryParam);
        modelAndView.addObject("resultListOfDay", resultListOfDay);
        modelAndView.addObject("resultListOfRange", resultListOfRange);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        StockBySourceQueryDto queryParam = JsonUtils.fromJson(queryParamStr, StockBySourceQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("仓库出入库来源汇总报表(" + queryParam.getDateRangeString() + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

            queryParam.parseDateRangeString();
            List<StockBySourceTableDto> resultListOfDay = stockService.selectStockBySourceOfDay(queryParam);
            List<StockBySourceTableDto> resultListOfRange = stockService.selectStockBySourceOfRange(queryParam);
            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("明细", StockBySourceTableDto.generateTableHeader(), StockBySourceTableDto.generateTableData(resultListOfDay)));
            sheetDataList.add(new ExcelUtils.SheetData("汇总", StockBySourceTableDto.generateTableHeader(), StockBySourceTableDto.generateTableData(resultListOfRange)));
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
