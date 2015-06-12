package com.iuni.data.ws.controller;

import com.iuni.data.ws.service.AlipayService;
import com.iuni.data.ws.service.SignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * sign: hmac(secret + data + timestamp)
 * signTime : 3des(secret + timestamp)
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@RestController
@RequestMapping("/alipay")
public class AlipayDataController {

    private static final Logger logger = LoggerFactory.getLogger(AlipayDataController.class);

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private AlipayService alipayService;


    @RequestMapping("/trade")
    @ResponseBody
    public String reportTrade(HttpServletRequest request) throws Exception {
        if (!signatureService.checkSign(request)) {
            logger.error("sign is not correct. ");
            return "error";
        }
        if (!alipayService.handleAlipayTrade(request.getParameter("data")))
            return "error";
        return "ok";
    }

    @RequestMapping("/report")
    @ResponseBody
    public String reportRecord(HttpServletRequest request) throws Exception {
        if (!signatureService.checkSign(request)) {
            logger.error("sign is not correct. ");
            return "error";
        }
        if (!alipayService.handleAlipayRecord(request.getParameter("data")))
            return "error";
        return "ok";
    }

}
