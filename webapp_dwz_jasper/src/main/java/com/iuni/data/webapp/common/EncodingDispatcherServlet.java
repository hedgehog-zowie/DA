package com.iuni.data.webapp.common;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class EncodingDispatcherServlet extends DispatcherServlet {
    private String encoding;

    public void init(ServletConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
        super.init(config);
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding(encoding);
        super.doService(request, response);
    }
}