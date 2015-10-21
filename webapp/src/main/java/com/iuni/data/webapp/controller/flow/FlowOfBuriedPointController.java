package com.iuni.data.webapp.controller.flow;

import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.flow.FlowOfBuriedPointService;
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
import java.util.Date;
import java.util.List;

/**
 * 埋点流量统计
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/flow/buriedPoint")
public class FlowOfBuriedPointController {

    private static final Logger logger = LoggerFactory.getLogger(FlowOfBuriedPointController.class);

    @Autowired
    private FlowOfBuriedPointService flowOfBuriedPointService;

    @RequestMapping
    public ModelAndView queryTable() {
        FlowOfBuriedPointForQueryDto queryParam = new FlowOfBuriedPointForQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    /**
     * 埋点流量历史统计
     *
     * @return
     */
    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") FlowOfBuriedPointForQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.flow_buried_point.getPath());
        StringUtils.parseDateRangeString(queryParam);
//        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromOracle(queryParam);
        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    /**
     * 埋点流量历史统计导出Excel
     *
     * @param queryParamStr
     * @param response
     * @return
     */
    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        FlowOfBuriedPointForQueryDto queryParam = JsonUtils.fromJson(queryParamStr, FlowOfBuriedPointForQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("埋点流量统计(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            StringUtils.parseDateRangeString(queryParam);
            List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(queryParam);

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(FlowOfBuriedPointForTableDto.generateTableHeader(), FlowOfBuriedPointForTableDto.generateTableData(resultList));
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

    /**
     * 埋点流量日实时统计
     *
     * @return
     */
    @RequestMapping("today")
    public ModelAndView queryTableToday() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.flow_buried_point_today.getPath());

//        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromOracle(generateTodayParams());
        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(generateTodayParams());
        modelAndView.addObject("resultList", resultList);
        return modelAndView;
    }

    /**
     * 埋点流量日实时统计导出Excel
     *
     * @param response
     * @return
     */
    @RequestMapping("today/exportExcel")
    public String exportTodayToExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        Date date = new Date();
        try {
            String fileName = new String(("埋点流量日实时统计(" + DateUtils.dateToSimpleDateStr(date, "yyyyMMdd") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(generateTodayParams());

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(FlowOfBuriedPointForTableDto.generateTableHeader(), FlowOfBuriedPointForTableDto.generateTableData(resultList));
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

    private FlowOfBuriedPointForQueryDto generateTodayParams() {
        FlowOfBuriedPointForQueryDto queryParam = new FlowOfBuriedPointForQueryDto();
        Date date = new Date();
        queryParam.setStartDateStr(DateUtils.dateToSimpleDateStr(date, "yyyy/MM/dd"));
        queryParam.setEndDateStr(DateUtils.dateToSimpleDateStr(date, "yyyy/MM/dd"));
        return queryParam;
    }

}
