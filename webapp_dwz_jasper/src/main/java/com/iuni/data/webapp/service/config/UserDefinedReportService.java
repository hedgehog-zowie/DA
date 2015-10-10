package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.UserDefinedReport;
import com.iuni.data.webapp.common.PageVO;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface UserDefinedReportService {

    UserDefinedReport getUserDefinedReportById(Long id);

    List<UserDefinedReport> listCurrentUserDefinedReport();

    List<UserDefinedReport> listCurrentUserDefinedReport(PageVO page, UserDefinedReport userDefinedReport);

    List<UserDefinedReport> listUserDefinedReport(PageVO page, UserDefinedReport userDefinedReport);

    boolean addUserDefinedReport(UserDefinedReport userDefinedReport);

    boolean updateUserDefinedReport(UserDefinedReport userDefinedReport);

    boolean deleteUserDefinedReport(String ids);

    boolean enableOrDisableUserDefinedReport(String ids, Integer status);

}
