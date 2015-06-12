package com.iuni.data.ws.service.impl;

import com.iuni.data.common.CryptUtils;
import com.iuni.data.ws.service.SignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class SignatureServiceImpl implements SignatureService {

    private static final Logger logger = LoggerFactory.getLogger(SignatureService.class);

    private static String oldSign = "";
    @Value("${secret}")
    private String secret;

    public boolean checkSign(HttpServletRequest request){
        String sign = request.getParameter("sign");
        if (sign == null || oldSign.equals(sign))
            return false;
        String timestamp = null;
        String data = null;
        String nSign = null;
        try {
            timestamp = CryptUtils.decrypt3DES(request.getParameter("code"), secret);
            data = CryptUtils.decrypt3DES(request.getParameter("data"), secret);
            nSign = CryptUtils.generateSign(data, timestamp, secret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!sign.equals(nSign))
            return false;
        return true;
    }

}
