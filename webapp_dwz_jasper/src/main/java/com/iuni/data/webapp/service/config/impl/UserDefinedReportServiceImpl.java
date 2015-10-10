package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.config.QUserDefinedReport;
import com.iuni.data.persist.domain.config.UserDefinedReport;
import com.iuni.data.persist.repository.config.UserDefinedReportRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.UserDefinedReportService;
import com.iuni.data.webapp.sso.service.AccountService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class UserDefinedReportServiceImpl implements UserDefinedReportService {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedReportService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDefinedReportRepository reportRepository;

    @Override
    public UserDefinedReport getUserDefinedReportById(Long id) {
        UserDefinedReport report = null;
        try {
            report = reportRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get report by id error. msg:" + e.getLocalizedMessage());
        }
        return report;
    }

    @Override
    public List<UserDefinedReport> listCurrentUserDefinedReport() {
        List<UserDefinedReport> reportList = new ArrayList<>();

        UserDefinedReport userDefinedReport = new UserDefinedReport();

        userDefinedReport.setCancelFlag(0);
        userDefinedReport.setStatus(1);
        userDefinedReport.setUser(accountService.getCurrentUser().getLoginName());

        BooleanExpression booleanExpression = generateExpression(userDefinedReport);
        try {
            if (booleanExpression == null) {
                Sort sort = new Sort(Sort.Direction.ASC, new String[]{"name"});
                reportList = reportRepository.findAll(sort);
            } else {
                for (UserDefinedReport report : reportRepository.findAll(booleanExpression, QUserDefinedReport.userDefinedReport.name.asc()))
                    reportList.add(report);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return reportList;
    }

    @Override
    public List<UserDefinedReport> listCurrentUserDefinedReport(PageVO page, UserDefinedReport userDefinedReport) {
        userDefinedReport.setUser(accountService.getCurrentUser().getLoginName());
        return listUserDefinedReport(page, userDefinedReport);
    }

    @Override
    public List<UserDefinedReport> listUserDefinedReport(PageVO page, UserDefinedReport userDefinedReport) {
        List<UserDefinedReport> reportList = null;

        BooleanExpression booleanExpression = generateExpression(userDefinedReport);
        try {
            Sort sort = new Sort(Sort.Direction.ASC, new String[]{"name"});
            Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize(), sort);
            Page resultPage;
            if (booleanExpression == null)
                resultPage = reportRepository.findAll(pageable);
            else
                resultPage = reportRepository.findAll(booleanExpression, pageable);
            page.setPage(resultPage);
            reportList = resultPage.getContent();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return reportList;
    }

    @Override
    public boolean addUserDefinedReport(UserDefinedReport userDefinedReport) {
        String currentUser = accountService.getCurrentUser().getLoginName();
        userDefinedReport.setUser(currentUser);
        userDefinedReport.setBasicInfoForCreate(currentUser);
        return saveUserDefinedReport(userDefinedReport);
    }

    @Override
    public boolean updateUserDefinedReport(UserDefinedReport userDefinedReport) {
        UserDefinedReport oldReport = getUserDefinedReportById(userDefinedReport.getId());
        if (oldReport != null) {
            userDefinedReport.setCreateDate(oldReport.getCreateDate());
            userDefinedReport.setCreateBy(oldReport.getCreateBy());
            userDefinedReport.setUser(oldReport.getUser());
            userDefinedReport.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveUserDefinedReport(userDefinedReport);
    }

    @Override
    public boolean deleteUserDefinedReport(String ids) {
        List<UserDefinedReport> reportList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                UserDefinedReport userDefinedReport = reportRepository.findOne(Long.parseLong(id));
                if (userDefinedReport != null) {
                    // set cancel flag to 1, logical delete it.
                    userDefinedReport.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    reportList.add(userDefinedReport);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete report error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveUserDefinedReport(reportList);
    }

    @Override
    public boolean enableOrDisableUserDefinedReport(String ids, Integer status) {
        return false;
    }

    private BooleanExpression generateExpression(UserDefinedReport userDefinedReport) {
        BooleanExpression booleanExpression = null;
        QUserDefinedReport qUserDefinedReport = QUserDefinedReport.userDefinedReport;
        if (userDefinedReport.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qUserDefinedReport.cancelFlag.eq(userDefinedReport.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (userDefinedReport.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qUserDefinedReport.status.eq(userDefinedReport.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(userDefinedReport.getName())) {
            BooleanExpression nameBooleanExpression = qUserDefinedReport.name.like("%" + userDefinedReport.getName() + "%");
            booleanExpression = (booleanExpression == null ? nameBooleanExpression : booleanExpression.and(nameBooleanExpression));
        }
        if (StringUtils.isNotBlank(userDefinedReport.getUser())) {
            BooleanExpression userBooleanExpression = qUserDefinedReport.user.eq(userDefinedReport.getUser());
            booleanExpression = (booleanExpression == null ? userBooleanExpression : booleanExpression.and(userBooleanExpression));
        }
        return booleanExpression;
    }

    private boolean saveUserDefinedReport(UserDefinedReport report) {
        try {
            reportRepository.save(report);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveUserDefinedReport(List<UserDefinedReport> reportList) {
        try {
            reportRepository.save(reportList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

}
