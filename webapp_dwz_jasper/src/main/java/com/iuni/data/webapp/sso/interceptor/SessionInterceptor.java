package com.iuni.data.webapp.sso.interceptor;

import com.iuni.data.webapp.sso.constants.SsoConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断session是否超时的拦截器
 * 超时跳到登录页面
 *
 * @author kevin
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final long serialVersionUID = 7579862236766378267L;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //ShiroUser user = (ShiroUser) request.getSession().getAttribute("user");
        String userName = (String) request.getSession().getAttribute("userName");
        if (StringUtils.isBlank(userName)) {
            //Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(request
                    .getHeader("X-Requested-With"))
                    || request.getParameter("ajax") != null) {
                String msg = "{\"statusCode\":\"301\", \"message\":\"Session Timeout! Please re-sign in!\"}";
                response.getWriter().write(msg);
                return false;
            } else {
                //非Ajax请求
                String callbackUrl = "http://" + request.getServerName() + ":"
                        + request.getServerPort() + request.getContextPath()
                        + SsoConstant.UUC_AUTHC_CALLBACK_URI;
                String ssoUrl = SsoConstant.UUC_URL
                        + SsoConstant.UUC_AUTHC_TICKET_URI + "service="
                        + callbackUrl;
                // 调用IUC SSO接口，发送重定向 ，类似：
                // ssoUrl=http://passport.cm.com/iuc/sso?service=http://18.8.0.28:8080/wms/authcCallback
                response.sendRedirect(response.encodeRedirectURL(ssoUrl));
                return false;
            }
        }
        return true;
    }
}