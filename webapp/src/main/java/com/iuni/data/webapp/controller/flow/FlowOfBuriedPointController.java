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
import java.util.*;

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
//        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPoints(queryParam);
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
            List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPoints(queryParam);

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(generateTableHeaders(), generateTableDatas(resultList));
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

//        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPoints(generateTodayParams());
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

            List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPoints(generateTodayParams());

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(generateTableHeaders(), generateTableDatas(resultList));
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
        queryParam.setEndDateStr(DateUtils.dateToSimpleDateStr(DateUtils.computeStartDate(date, 1), "yyyy/MM/dd"));
        return queryParam;
    }

    /**
     * 表头
     *
     * @return
     */
    private Map<String, String> generateTableHeaders() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "day");
        tableHeader.put("站点名称", "website");
        tableHeader.put("页面名称", "pageName");
        tableHeader.put("页面位置", "pagePosition");
        tableHeader.put("埋点编码", "pointFlag");
        tableHeader.put("PV", "pv");
        tableHeader.put("UV", "uv");
        tableHeader.put("VV", "vv");
        tableHeader.put("IP", "ip");
        return tableHeader;
    }

    /**
     * 表数据
     *
     * @param flowOfBuriedPointTableDtoList
     * @return
     */
    private List<Map<String, String>> generateTableDatas(List<FlowOfBuriedPointForTableDto> flowOfBuriedPointTableDtoList) {
        List<Map<String, String>> tableDatas = new ArrayList<>();
        for (FlowOfBuriedPointForTableDto flowOfBuriedPointTableDto : flowOfBuriedPointTableDtoList) {
            Map<String, String> rowData = new HashMap<>();
            rowData.put("day", flowOfBuriedPointTableDto.getDay());
            rowData.put("website", flowOfBuriedPointTableDto.getWebsite());
            rowData.put("pageName", flowOfBuriedPointTableDto.getPageName());
            rowData.put("pagePosition", flowOfBuriedPointTableDto.getPagePosition());
            rowData.put("pointFlag", flowOfBuriedPointTableDto.getPointFlag());
            rowData.put("pv", String.valueOf(flowOfBuriedPointTableDto.getPv()));
            rowData.put("uv", String.valueOf(flowOfBuriedPointTableDto.getUv()));
            rowData.put("vv", String.valueOf(flowOfBuriedPointTableDto.getVv()));
            rowData.put("ip", String.valueOf(flowOfBuriedPointTableDto.getIp()));
            tableDatas.add(rowData);
        }
        return tableDatas;
    }

}
