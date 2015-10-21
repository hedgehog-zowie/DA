package com.iuni.data.webapp.controller.active;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;
import com.iuni.data.persist.model.activity.ActivityChannelTableDto;
import com.iuni.data.persist.model.activity.ActivityChannelQueryDto;
import com.iuni.data.persist.model.activity.ActivityChannelChartDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.activity.ActivityService;
import com.iuni.data.webapp.service.config.ChannelTypeService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/activity/channel")
public class ChannelController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ChannelTypeService channelTypeService;

    @RequestMapping
    public ModelAndView queryTable() {
        ActivityChannelQueryDto queryParam = new ActivityChannelQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") ActivityChannelQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.active_channel.getPath());
        StringUtils.parseDateRangeString(queryParam);
        List<ActivityChannelTableDto> resultList = activityService.selectActivityChannel(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);

        ChannelType channelType = new ChannelType();
        channelType.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        channelType.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        modelAndView.addObject("channelTypes", channelTypeService.listChannelType(channelType));
        return modelAndView;
    }

    @RequestMapping("data")
    @ResponseBody
    public Map<String, Object> queryChartData(@RequestBody ActivityChannelQueryDto queryParam) {
        StringUtils.parseDateRangeString(queryParam);
        Map<String, Object> modelMap = new HashMap<>(2);
        ActivityChannelQueryDto.DataType dataType = ActivityChannelQueryDto.DataType.valueOf(queryParam.getDataType());
        List<ActivityChannelChartDto> seriesData = new ArrayList<>();
        switch (dataType) {
            case on:
                seriesData = activityService.selectOrderByActivityChannel(queryParam);
                break;
            case pn:
                seriesData = activityService.selectPaidOrderByActivityChannel(queryParam);
                break;
            case pv:
                seriesData = activityService.selectPVByActivityChannel(queryParam);
                break;
            case uv:
                seriesData = activityService.selectUVByActivityChannel(queryParam);
                break;
            case vv:
                seriesData = activityService.selectVVByActivityChannel(queryParam);
                break;
            default:
                break;
        }
        List<String> legendData = new ArrayList<>(seriesData.size());
        for (ActivityChannelChartDto echartsValueDto : seriesData)
            legendData.add(echartsValueDto.getName());
        modelMap.put("title", dataType.getName());
        modelMap.put("legendData", legendData);
        modelMap.put("seriesData", seriesData);
        return modelMap;
    }

    /**
     * 活动-渠道分析导出Excel
     *
     * @param response
     * @return
     */
    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        ActivityChannelQueryDto queryParam = JsonUtils.fromJson(queryParamStr, ActivityChannelQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("活动-渠道分析(" + queryParam.getDateRangeString().replaceAll("\\s+","") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            StringUtils.parseDateRangeString(queryParam);
            List<ActivityChannelTableDto> resultList = activityService.selectActivityChannel(queryParam);

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(ActivityChannelTableDto.generateTableHeader(), ActivityChannelTableDto.generateTableData(resultList));
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
