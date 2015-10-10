package com.iuni.data.webapp.util;

import com.iuni.data.common.ConfigConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class ReportFileUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReportFileUtils.class);

//    private static String endFix = ".jasper";

    public static String saveFile(MultipartFile reportFile) {
        File file = new File(System.getenv(ConfigConstants.ENV_REPORT_HOME), reportFile.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(reportFile.getInputStream(), file);
            if(!compileReportFile(file)) {
                FileUtils.forceDelete(file);
                return null;
            }
        } catch (IOException e) {
            logger.error("save report file error.", e);
            return null;
        }
        return file.getAbsolutePath();
    }

    private static boolean compileReportFile(File file) {
        try {
            JRLoader.loadObject(file);
        } catch (JRException e) {
            logger.error("report file is illegal, please check it.");
            return false;
        }
        return true;
    }

}
