package com.iuni.data.webapp.controller;

import com.iuni.data.persist.domain.config.UserDefinedReport;
import com.iuni.data.webapp.service.config.UserDefinedReportService;
import com.iuni.data.webapp.sso.constants.SsoConstant;
import com.iuni.data.webapp.sso.dto.Menu;
import com.iuni.data.webapp.sso.dto.ShiroUser;
import com.iuni.data.webapp.sso.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDefinedReportService reportFileService;

    @RequestMapping("login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        ShiroUser user = accountService.getCurrentUser();
        List<Menu> menuList = user.getMenuList();
        modelAndView.addObject("menuList", menuList);

        // find all user defined reports
        List<UserDefinedReport> userDefinedReports = reportFileService.listCurrentUserDefinedReport();
        modelAndView.addObject("userDefinedReports", userDefinedReports);

        return modelAndView;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityUtils.getSubject().logout();
        String callbackUrl = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath()
                + SsoConstant.UUC_AUTHC_CALLBACK_URI;
        String ssoUrl = SsoConstant.UUC_URL + SsoConstant.UUC_AUTHC_LOGOUT_URI + "s=y" + "&service=" + callbackUrl;
        response.sendRedirect(response.encodeRedirectURL(ssoUrl));
        return null;
    }

}
