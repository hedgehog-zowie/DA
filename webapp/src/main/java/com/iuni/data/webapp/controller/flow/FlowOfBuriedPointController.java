package com.iuni.data.webapp.controller.flow;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.BuriedGroup;
import com.iuni.data.persist.model.financial.StockMoveDetailsTableDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForQueryDto;
import com.iuni.data.persist.model.flow.FlowOfBuriedPointForTableDto;
import com.iuni.data.utils.DateUtils;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.config.BuriedGroupService;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private BuriedGroupService buriedGroupService;
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

        queryParam.parseDateRangeString();
        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(queryParam);
        modelAndView.addObject("resultList", resultList);

        BuriedGroup buriedGroup = new BuriedGroup();
        buriedGroup.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        List<BuriedGroup> buriedGroupList = buriedGroupService.listBuriedGroup(buriedGroup);
        modelAndView.addObject("buriedGroupList", buriedGroupList);

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
        String fileName = "埋点流量统计(" + queryParam.getDateRangeString() + ")";
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode file name error.", e.getLocalizedMessage());
        }
        return exportExcel(response, queryParam, fileName);

    }

    /**
     * 埋点流量日实时统计
     *
     * @return
     */
    @RequestMapping("today")
    public ModelAndView queryTableToday() {
        FlowOfBuriedPointForQueryDto queryParam = new FlowOfBuriedPointForQueryDto();
        return queryTableToday(queryParam);
    }

    /**
     * 埋点流量日实时统计
     *
     * @return
     */
    @RequestMapping("today/query")
    public ModelAndView queryTableToday(@ModelAttribute("queryParam") FlowOfBuriedPointForQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.flow_buried_point_today.getPath());

        queryParam.setDateRangeString(StringUtils.getTodayRangeString());
        queryParam.parseDateRangeString();
        List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(queryParam);
        modelAndView.addObject("resultList", resultList);

        BuriedGroup buriedGroup = new BuriedGroup();
        buriedGroup.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        List<BuriedGroup> buriedGroupList = buriedGroupService.listBuriedGroup(buriedGroup);
        modelAndView.addObject("buriedGroupList", buriedGroupList);

        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    /**
     * 埋点流量日实时统计导出Excel
     *
     * @param response
     * @return
     */
    @RequestMapping("today/exportExcel")
    public String exportTodayToExcel(String queryParamStr, HttpServletResponse response) {
        FlowOfBuriedPointForQueryDto queryParam = JsonUtils.fromJson(queryParamStr, FlowOfBuriedPointForQueryDto.class);
        queryParam.setDateRangeString(StringUtils.getTodayRangeString());
        String fileName = "埋点流量日实时统计(" + queryParam.getDateRangeString() + ")";
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode file name error.", e.getLocalizedMessage());
        }
        return exportExcel(response, queryParam, fileName);
    }

    private String exportExcel(HttpServletResponse response, FlowOfBuriedPointForQueryDto queryParam, String fileName) {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
        try {
            queryParam.parseDateRangeString();
            List<FlowOfBuriedPointForTableDto> resultList = flowOfBuriedPointService.selectFlowOfBuriedPointsFromHbase(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("埋点流量日实时统计", FlowOfBuriedPointForTableDto.generateTableHeader(), FlowOfBuriedPointForTableDto.generateTableData(resultList)));
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
