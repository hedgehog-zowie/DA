package com.iuni.data.ws.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface SignatureService {

    boolean checkSign(HttpServletRequest request);

}
