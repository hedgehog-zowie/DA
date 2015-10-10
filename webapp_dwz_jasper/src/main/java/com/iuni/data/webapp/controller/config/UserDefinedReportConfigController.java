package com.iuni.data.webapp.controller.config;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.UserDefinedReport;
import com.iuni.data.webapp.annotation.FormModel;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.response.StatusResponse;
import com.iuni.data.webapp.service.config.UserDefinedReportService;
import com.iuni.data.webapp.util.ReportFileUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/config/userDefinedReport")
public class UserDefinedReportConfigController {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedReportConfigController.class);

    @Autowired
    private UserDefinedReportService reportService;

    /**
     * 返回UserDefinedReport配置页面，分页显示UserDefinedReport
     *
     * @param currentPage
     * @param pageSize
     * @param userDefinedReport
     * @return
     */
    @RequestMapping
    public ModelAndView listUserDefinedReport(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                              @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                              @FormModel("userDefinedReport") UserDefinedReport userDefinedReport) {
        userDefinedReport.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        List<UserDefinedReport> userDefinedReportList = reportService.listCurrentUserDefinedReport(page, userDefinedReport);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_user_defined_report.getPath());

        modelAndView.addObject("userDefinedReportList", userDefinedReportList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 返回添加/编辑页面，新建一个UserDefinedReport对象
     *
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addUserDefinedReport() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_user_defined_report_edit.getPath());
        UserDefinedReport userDefinedReport = new UserDefinedReport();
        modelAndView.addObject("userDefinedReport", userDefinedReport);
        return modelAndView;
    }

    /**
     * 返回添加/编辑页面，查找UserDefinedReport对象返回页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editUserDefinedReport(@RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_user_defined_report_edit.getPath());
        UserDefinedReport userDefinedReport = reportService.getUserDefinedReportById(id);
        modelAndView.addObject("userDefinedReport", userDefinedReport);
        modelAndView.addObject("reportFile", userDefinedReport.getPath());
        return modelAndView;
    }

    /**
     * 保存
     *
     * @param name
     * @param desc
     * @param reportFile
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse saveUserDefinedReport(long id, String name, String desc, MultipartFile reportFile) {
        String filePath = ReportFileUtils.saveFile(reportFile);
        if(filePath == null)
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "保存报表文件失败");

        UserDefinedReport userDefinedReport = new UserDefinedReport();
        userDefinedReport.setId(id);
        userDefinedReport.setName(name);
        userDefinedReport.setDesc(desc);
        userDefinedReport.setPath(filePath);
        if (userDefinedReport.getId() == 0) {
            if (reportService.addUserDefinedReport(userDefinedReport))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "添加失败");
        } else {
            if (reportService.updateUserDefinedReport(userDefinedReport))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "修改失败");
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public StatusResponse deleteUserDefinedReport(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete user defined reports. ids: {}", ids);
        if (!reportService.deleteUserDefinedReport(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功");
    }

}
