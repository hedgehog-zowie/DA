package com.iuni.data.webapp.controller.userDefined;

import com.iuni.data.persist.domain.config.UserDefinedReport;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.common.ReportFile;
import com.iuni.data.webapp.controller.ReportController;
import com.iuni.data.webapp.service.ReportService;
import com.iuni.data.webapp.service.config.UserDefinedReportService;
import com.iuni.data.webapp.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户自定义报表
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/userDefined")
public class UserDefinedController extends ReportController {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedController.class);

    @Autowired
    private ReportService reportService;
    @Autowired
    private UserDefinedReportService reportFileService;

    @RequestMapping
    public ModelAndView userDefined(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                    @RequestParam(value = "reportId", required = false) Long reportId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userDefined");

        currentPage = ValidateUtils.checkPage(currentPage);
        PageVO page = new PageVO();
        page.setCurrentPage(currentPage);

        UserDefinedReport userDefinedReport = reportFileService.getUserDefinedReportById(reportId);
        String reportContent;
        try {
            reportContent = reportService.report(userDefinedReport.getPath(), null, false, page);
        } catch (Exception e) {
            logger.error("report error. {}", e);
            reportContent = e.getLocalizedMessage();
        }

        modelAndView.addObject("report", reportContent);
        modelAndView.addObject("page", page);
        modelAndView.addObject("reportId", reportId);
        return modelAndView;
    }

    @RequestMapping("/download")
    public void download(@RequestParam(value = "reportId", required = false) Long reportId,
                         @RequestParam(value = "type", required = false) String type,
                         HttpServletResponse response) throws SQLException {
        type = ValidateUtils.checkExportType(type);
        UserDefinedReport userDefinedReport = reportFileService.getUserDefinedReportById(reportId);
        try {
            reportService.download(userDefinedReport.getName(), userDefinedReport.getPath(), type, response, null, false);
        } catch (Exception e) {
            logger.error("download report error. {}", e);
        }
    }

}
