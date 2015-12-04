package com.iuni.data.webapp.controller.distribution;

import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsQueryDto;
import com.iuni.data.persist.model.distribution.ReturnGoodsDetailsTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.distribution.ReceiveService;
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
@Controller("returnGoodsDetailsControllerOfDistribution")
@RequestMapping("/distribution/returnGoodsDetails")
public class ReturnGoodsDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(ReturnGoodsDetailsController.class);

    @Autowired
    private ReceiveService receiveService;

    @RequestMapping
    public ModelAndView queryTable() {
        ReturnGoodsDetailsQueryDto queryParam= new ReturnGoodsDetailsQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") ReturnGoodsDetailsQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.distribution_return_goods_details.getPath());
        queryParam.parseDateRangeString();
        List<ReturnGoodsDetailsTableDto> resultList = receiveService.selectReturnGoodsDetails(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        ReturnGoodsDetailsQueryDto queryParam = JsonUtils.fromJson(queryParamStr, ReturnGoodsDetailsQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("退货明细报表(" + queryParam.getDateRangeString() + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

            queryParam.parseDateRangeString();
            List<ReturnGoodsDetailsTableDto> resultList = receiveService.selectReturnGoodsDetails(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("退货明细", ReturnGoodsDetailsTableDto.generateTableHeader(), ReturnGoodsDetailsTableDto.generateTableData(resultList)));
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
