package com.iuni.data.webapp.util;

import com.iuni.data.webapp.common.ExportType;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ValidateUtils {

    public static String checkExportType(String type){
        if(null == type)
            type = ExportType.xlsx.getType();
        return type;
    }

    public static Integer checkPage(Integer page){
        if(null == page)
            page = 1;
        return page;
    }

}
