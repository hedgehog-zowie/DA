package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.*;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.distribution.ReverseService;
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
@Controller("reverseSignControllerOfDistribution")
@RequestMapping("/distribution/reverseSign")
public class ReverseSignController {

    private static final Logger logger = LoggerFactory.getLogger(ReverseSignController.class);

    @Autowired
    private ReverseService reverseService;

    @RequestMapping
    public ModelAndView queryTable() {
        ReverseSignQueryDto queryParam= new ReverseSignQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") ReverseSignQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_reverse_sign.getPath());
        queryParam.parseDateRangeString();
        List<ReverseSignOfBackTableDto> resultListOfBack = reverseService.selectReverseSignOfBack(queryParam);
        List<ReverseSignOfExchangeTableDto> resultListOfExchange = reverseService.selectReverseSignOfExchange(queryParam);
        List<ReverseSignOfRepairTableDto> resultListOfRepair = reverseService.selectReverseSignOfRepair(queryParam);
        modelAndView.addObject("resultListOfBack", resultListOfBack);
        modelAndView.addObject("resultListOfExchange", resultListOfExchange);
        modelAndView.addObject("resultListOfRepair", resultListOfRepair);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        ReverseSignQueryDto queryParam = JsonUtils.fromJson(queryParamStr, ReverseSignQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("逆向签收表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<ReverseSignOfBackTableDto> resultListOfBack = reverseService.selectReverseSignOfBack(queryParam);
            List<ReverseSignOfExchangeTableDto> resultListOfExchange = reverseService.selectReverseSignOfExchange(queryParam);
            List<ReverseSignOfRepairTableDto> resultListOfRepair = reverseService.selectReverseSignOfRepair(queryParam);
            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("退货", ReverseSignOfBackTableDto.generateTableHeader(), ReverseSignOfBackTableDto .generateTableData(resultListOfBack)));
            sheetDataList.add(new ExcelUtils.SheetData("换货", ReverseSignOfExchangeTableDto.generateTableHeader(), ReverseSignOfExchangeTableDto .generateTableData(resultListOfExchange)));
            sheetDataList.add(new ExcelUtils.SheetData("维修", ReverseSignOfRepairTableDto.generateTableHeader(), ReverseSignOfRepairTableDto .generateTableData(resultListOfRepair)));
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
