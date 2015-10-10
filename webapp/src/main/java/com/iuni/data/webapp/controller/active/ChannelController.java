package com.iuni.data.webapp.controller.active;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.model.activity.ChannelTableDto;
import com.iuni.data.persist.model.activity.ChannelQueryDto;
import com.iuni.data.persist.model.activity.ChannelChartDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.activity.ActivityService;
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

    @RequestMapping
    public ModelAndView queryTable() {
        ChannelQueryDto queryParam = new ChannelQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") ChannelQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.active_channel.getPath());
        StringUtils.parseDateRangeString(queryParam);
        List<ChannelTableDto> resultList = activityService.selectActivityChannel(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        modelAndView.addObject("channelTypes", Channel.ChannelType.getAllChannelType());
        return modelAndView;
    }

    @RequestMapping("data")
    @ResponseBody
    public Map<String, Object> queryChartData(@RequestBody ChannelQueryDto queryParam) {
        StringUtils.parseDateRangeString(queryParam);
        Map<String, Object> modelMap = new HashMap<>(2);
        ChannelQueryDto.DataType dataType = ChannelQueryDto.DataType.valueOf(queryParam.getDataType());
        List<ChannelChartDto> seriesData = new ArrayList<>();
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
        for (ChannelChartDto echartsValueDto : seriesData)
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
        ChannelQueryDto queryParam = JsonUtils.fromJson(queryParamStr, ChannelQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("活动-渠道分析(" + queryParam.getDateRangeString().replaceAll("\\s+","") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            StringUtils.parseDateRangeString(queryParam);
            List<ChannelTableDto> resultList = activityService.selectActivityChannel(queryParam);

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
     * 表头
     * @return
     */
    private Map<String, String> generateTableHeaders() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("日期", "time");
        tableHeader.put("推广渠道", "channelName");
        tableHeader.put("推广链接", "channelUrl");
        tableHeader.put("PV", "pv");
        tableHeader.put("UV", "uv");
        tableHeader.put("VV", "vv");
        tableHeader.put("跳出率", "jumpRate");
        tableHeader.put("人均浏览页面", "avrPages");
        tableHeader.put("平均访问深度", "avrDeeps");
        tableHeader.put("平均访问时间", "avrTimes");
        tableHeader.put("注册页UV", "ruv");
        tableHeader.put("注册成功数", "rsNum");
        tableHeader.put("注册转化率", "rRate");
        tableHeader.put("注册成功率", "rsRate");
        tableHeader.put("下单总数量", "orderNum");
        tableHeader.put("下单总金额", "orderAmount");
        tableHeader.put("下单转化率", "orderTrans");
        tableHeader.put("已支付订单数", "paidOrderNum");
        tableHeader.put("已支付订单比", "payRate");
        tableHeader.put("已支付订单金额", "paidOrderAmount");
        tableHeader.put("客单价", "avgAmount");
        return tableHeader;
    }

    /**
     * 表数据
     * @param channelTableDtoList
     * @return
     */
    private List<Map<String, String>> generateTableDatas(List<ChannelTableDto> channelTableDtoList) {
        List<Map<String, String>> tableDatas = new ArrayList<>();
        for (ChannelTableDto channelTableDto : channelTableDtoList) {
            Map<String, String> rowData = new HashMap<>();
            rowData.put("time", channelTableDto.getTime().toString());
            rowData.put("channelName", channelTableDto.getChannelName());
            rowData.put("channelUrl", channelTableDto.getChannelUrl());
            rowData.put("pv", String.valueOf(channelTableDto.getPv()));
            rowData.put("uv", String.valueOf(channelTableDto.getUv()));
            rowData.put("vv", String.valueOf(channelTableDto.getVv()));
            rowData.put("jumpRate", String.valueOf(channelTableDto.getJumpRate()));
            rowData.put("avrPages", String.valueOf(channelTableDto.getAvrPages()));
            rowData.put("avrDeeps", String.valueOf(channelTableDto.getAvrDeeps()));
            rowData.put("avrTimes", String.valueOf(channelTableDto.getAvrTimes()));
            rowData.put("ruv", String.valueOf(channelTableDto.getRuv()));
            rowData.put("rsNum", String.valueOf(channelTableDto.getRsNum()));
            rowData.put("rRate", String.valueOf(channelTableDto.getrRate()));
            rowData.put("rsRate", String.valueOf(channelTableDto.getRsRate()));
            rowData.put("orderNum", String.valueOf(channelTableDto.getOrderNum()));
            rowData.put("orderAmount", String.valueOf(channelTableDto.getOrderAmount()));
            rowData.put("orderTrans", String.valueOf(channelTableDto.getOrderTrans()));
            rowData.put("paidOrderNum", String.valueOf(channelTableDto.getPaidOrderAmount()));
            rowData.put("payRate", String.valueOf(channelTableDto.getPayRate()));
            rowData.put("paidOrderAmount", String.valueOf(channelTableDto.getPaidOrderAmount()));
            rowData.put("avgAmount", String.valueOf(channelTableDto.getAvgAmount()));
            tableDatas.add(rowData);
        }
        return tableDatas;
    }

}
