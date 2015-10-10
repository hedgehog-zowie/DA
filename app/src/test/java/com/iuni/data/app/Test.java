package com.iuni.data.app;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.out.println(URLDecoder.decode(args[0], "UTF-8").replace("\r\n", "").replace("\n", ""));

    }

}
