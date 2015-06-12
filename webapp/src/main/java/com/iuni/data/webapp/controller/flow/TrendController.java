package com.iuni.data.webapp.controller.flow;

import com.iuni.data.common.DateUtils;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.common.ReportFile;
import com.iuni.data.webapp.chart.Series;
import com.iuni.data.webapp.service.ReportService;
import com.iuni.data.webapp.service.webkpi.WebKpiService;
import com.iuni.data.webapp.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/flow/trend")
public class TrendController {

    private static final Logger logger = LoggerFactory.getLogger(TrendController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private WebKpiService webKpiService;

    private Date begin;
    private Date end;
    private PageVO page;

    @RequestMapping
    public ModelAndView view(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                             String startDate, String endDate) {
        ModelAndView modelAndView = new ModelAndView();

        initParams(startDate, endDate, currentPage);

        modelAndView.addObject("page", page);
        startDate = DateUtils.dateToSimpleDateStr(begin, "yyyy-MM-dd");
        endDate = DateUtils.dateToSimpleDateStr(end, "yyyy-MM-dd");
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        String reportContent = reportService.report(ReportFile.flowTrend, params, false, page);
        modelAndView.addObject("report", reportContent);

        modelAndView.setViewName(PageName.flow_trend.getPath());
        return modelAndView;
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> data(String startDate, String endDate) {
        initParams(startDate, endDate, 0);
        List<WebKpi> results = webKpiService.getWebKpi(begin, end);

        Map<String, Object> modelMap = new HashMap<>(3);

        if(results.size() > 0) {
            // legend
            List<String> legendList = new ArrayList<>();
            // category
            List<String> categoryList = new ArrayList<>();
            // series
            List<Series> seriesList = new ArrayList<>();

            //add legend
            legendList.add("PV");
            legendList.add("UV");
            legendList.add("VV");
            legendList.add("NEW UV");

            // data
            List<Integer> pvData = new ArrayList<>();
            List<Integer> uvData = new ArrayList<>();
            List<Integer> vvData = new ArrayList<>();
            List<Integer> newUvData = new ArrayList<>();
            for (WebKpi webKpi : results) {
                // add category
                categoryList.add(DateUtils.dateToSimpleDateStr(webKpi.getTime(), "yy-MM-dd"));
                pvData.add(webKpi.getPv());
                uvData.add(webKpi.getUv());
                vvData.add(webKpi.getVv());
                newUvData.add(webKpi.getNewUv());
            }
            // series
            Series pvSeries = new Series();
            pvSeries.setName("PV");
            pvSeries.setType("line");
            pvSeries.setData(pvData);
            Series uvSeries = new Series();
            uvSeries.setName("UV");
            uvSeries.setType("line");
            uvSeries.setData(uvData);
            Series vvSeries = new Series();
            vvSeries.setName("VV");
            vvSeries.setType("line");
            vvSeries.setData(vvData);
            Series newUvSeries = new Series();
            newUvSeries.setName("NEW UV");
            newUvSeries.setType("line");
            newUvSeries.setData(newUvData);
            // add series
            seriesList.add(pvSeries);
            seriesList.add(uvSeries);
            seriesList.add(vvSeries);
            seriesList.add(newUvSeries);

            // put legend, category, series to result
            modelMap.put("legend", legendList);
            modelMap.put("category", categoryList);
            modelMap.put("series", seriesList);
        }

        return modelMap;
    }

    @RequestMapping("/download")
    public void download(String startDate, String endDate, String type, HttpServletResponse response) {
        initParams(startDate, endDate, 0);

        startDate = DateUtils.dateToSimpleDateStr(begin, "yyyy-MM-dd");
        endDate = DateUtils.dateToSimpleDateStr(end, "yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        type = ValidateUtils.checkExportType(type);
        try {
            reportService.download(ReportFile.flowTrend, type, response, params, false);
        } catch (Exception e) {
            logger.error("download report error. {}", e);
        }
    }

    private void initParams(String startDate, String endDate, Integer currentPage) {
        if (endDate != null && !"".equals(endDate))
            end = DateUtils.computeEndDate(DateUtils.simpleDateStrToDate(endDate, "yyyy-MM-dd"), 0);
        else
            end = DateUtils.computeEndDate(new Date(), -1);

        if (startDate != null && !"".equals(startDate))
            begin = DateUtils.computeStartDate(DateUtils.simpleDateStrToDate(startDate, "yyyy-MM-dd"), 0);
        else
            begin = DateUtils.computeStartDate(new Date(), -7);

        page = new PageVO();
        page.setCurrentPage(ValidateUtils.checkPage(currentPage));
    }


}
