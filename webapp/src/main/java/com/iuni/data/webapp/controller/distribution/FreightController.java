package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.FreightQueryDto;
import com.iuni.data.persist.model.distribution.FreightTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
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
@Controller("freightControllerOfDistribution")
@RequestMapping("/distribution/freight")
public class FreightController {

    private static final Logger logger = LoggerFactory.getLogger(FreightController.class);

    @Autowired
    private ShippingService shippingService;

    @RequestMapping
    public ModelAndView queryTable() {
        FreightQueryDto queryParam= new FreightQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") FreightQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_freight.getPath());
        queryParam.parseDateRangeString();
        List<FreightTableDto> resultListOfForward = shippingService.selectFreightOfForward(queryParam);
        List<FreightTableDto> resultListOfReverse = shippingService.selectFreightOfReverse(queryParam);
        modelAndView.addObject("resultListOfForward", resultListOfForward);
        modelAndView.addObject("resultListOfReverse", resultListOfReverse);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        FreightQueryDto queryParam = JsonUtils.fromJson(queryParamStr, FreightQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("运费报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<FreightTableDto> resultListOfForward = shippingService.selectFreightOfForward(queryParam);
            List<FreightTableDto> resultListOfReserve = shippingService.selectFreightOfReverse(queryParam);
            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("正向", FreightTableDto.generateTableHeader(), FreightTableDto.generateTableData(resultListOfForward)));
            sheetDataList.add(new ExcelUtils.SheetData("逆向", FreightTableDto.generateTableHeader(), FreightTableDto.generateTableData(resultListOfReserve)));
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
