package com.iuni.data.ws.service;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface AlipayService {

    boolean handleAlipayTrade(String data);

    boolean handleAlipayRecord(String data);

}
