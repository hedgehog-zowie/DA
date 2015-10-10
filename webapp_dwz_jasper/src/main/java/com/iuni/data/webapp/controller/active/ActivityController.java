package com.iuni.data.webapp.controller.active;

import com.iuni.data.utils.DateUtils;
import com.iuni.data.common.TType;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.dto.ActivityKpi;
import com.iuni.data.webapp.service.activity.ActivityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/activity/flow")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    private String startDateStr;
    private String endDateStr;

    @RequestMapping()
    public ModelAndView activeFlow(@RequestParam(value = "url", required = false) String url,
                                   @RequestParam(value = "channelCode", required = false) String channelCode,
                                   @RequestParam(value = "pageNum", required = false) Integer currentPage,
                                   @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                   @RequestParam(value = "startDateStr", required = false) String startDateStr,
                                   @RequestParam(value = "endDateStr", required = false) String endDateStr
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.active_flow.getPath());
        modelAndView.addObject("url", url);
        Date startDate, endDate;
        if (StringUtils.isBlank(startDateStr)) {
            startDate = DateUtils.computeStartDate(new Date(), -7);
            modelAndView.addObject("startDate", DateUtils.dateToPageDateStr(startDate));
        } else {
            startDate = DateUtils.pageDateStrToDate(startDateStr);
            startDate = DateUtils.computeStartDate(startDate, 0);
            modelAndView.addObject("startDate", startDateStr);
        }
        if (StringUtils.isBlank(endDateStr)) {
            endDate = DateUtils.computeEndDate(new Date(), -1);
            modelAndView.addObject("endDate", DateUtils.dateToPageDateStr(endDate));
        } else {
            endDate = DateUtils.pageDateStrToDate(endDateStr);
            endDate = DateUtils.computeEndDate(endDate, 0);
            modelAndView.addObject("endDate", endDateStr);
        }
//        if(StringUtils.isBlank(channelCode))
//            channelCode = Constants.DEFAULT_CHANNEL_CODE;
        modelAndView.addObject("channelCode", channelCode);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        List<ActivityKpi> activityKpiList = activityService.findActivityKpi(url, channelCode, startDate, endDate, TType.DAY.getPattern(), page);
        if (activityKpiList.size() != 0) {
            List<String> columnList = new ArrayList<>();
            columnList.add("时间");
            columnList.add("PV");
            columnList.add("UV");
            if (activityKpiList.get(0).getCountMap() != null && activityKpiList.get(0).getCountMap().size() != 0)
                for (String c : activityKpiList.get(0).getCountMap().keySet())
                    columnList.add(c);
            modelAndView.addObject("rate", 100 / columnList.size());
            modelAndView.addObject("columnList", columnList);
            modelAndView.addObject("activeKpiList", activityKpiList);
        }
        return modelAndView;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

}
