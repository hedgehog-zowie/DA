package com.iuni.data.webapp.controller;

import com.iuni.data.webapp.sso.constants.SsoConstant;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/")
public class LoginController {

//    @Autowired
//    private AccountService accountService;

//    @RequestMapping("login")
//    public String login() {
//        return "redirect:/login";
//    }

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
