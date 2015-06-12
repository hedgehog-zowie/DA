package com.iuni.data.webapp.service;

import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.common.ReportFile;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ReportService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private ExporterService exporter;

    public String report(ReportFile reportFile, Map<String, Object> params, boolean showChart) {
        return report(reportFile, params, showChart, null);
    }

    public String report(String reportFilePath, Map<String, Object> params, boolean showChart, PageVO page) {
        try {
            JasperPrint jasperPrint = generateJP(reportFilePath, params, showChart);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (page != null)
                setPages(page, jasperPrint);
            exporter.exportHtml(page, jasperPrint, baos);

            return baos.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String report(ReportFile reportFile, Map<String, Object> params, boolean showChart, PageVO page) {
        try {
            JasperPrint jasperPrint = generateJP(reportFile, params, showChart);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (page != null)
                setPages(page, jasperPrint);
            exporter.exportHtml(page, jasperPrint, baos);

            return baos.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void download(String reportName, String reportFilePath, String type, HttpServletResponse response, Map<String, Object> params, boolean showChart) throws SQLException {
        try {
            JasperPrint jasperPrint = generateJP(reportFilePath, params, showChart);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.export(reportName, type, jasperPrint, response, baos);

            write(response, baos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void download(ReportFile reportFile, String type, HttpServletResponse response, Map<String, Object> params, boolean showChart) throws SQLException {
        try {
            params.put("Title", reportFile.getName());
            JasperPrint jasperPrint = generateJP(reportFile, params, showChart);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.export(reportFile.getName(), type, jasperPrint, response, baos);

            write(response, baos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JasperPrint generateJP(File reportFile, Map<String, Object> params){
        if (params == null)
            params = new HashMap();
        params.put("Title", reportFile.getName());

        JasperPrint jp;
        try {
//            JasperDesign jd = JRXmlLoader.load(file);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperReport jr = (JasperReport) JRLoader.loadObject(reportFile);

            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            DataSource ds = (DataSource) wac.getBean("jpaDataSource");
            Connection conn = ds.getConnection();

            jp = JasperFillManager.fillReport(jr, params, conn);
        } catch (Exception e) {
            logger.error("Unable to generate jasper print." + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return jp;
    }

    private JasperPrint generateJP(String reportFilePath, Map<String, Object> params, boolean showChart) {
        File file = new File(reportFilePath);
        if (null == file) {
            String errMsg = file.getPath() + " not found.";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        return generateJP(file, params);
    }

    private JasperPrint generateJP(ReportFile reportFile, Map<String, Object> params, boolean showChart) {
        String rootPath = System.getenv("REPORT_HOME") == null ? "" : System.getenv("REPORT_HOME");
        File file;
        if (showChart)
            file = new File(rootPath + "/" + reportFile.getChartPath());
        else
            file = new File(rootPath + "/" + reportFile.getPath());
        if (null == file) {
            String errMsg = reportFile.getPath() + " not found.";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        return generateJP(file, params);
    }

    private void setPages(PageVO page, JasperPrint jp) {
        page.setTotalPage(jp.getPages().size());
        long totalCount = 0;
        for (int i = 0; i < jp.getPages().size(); i++) {
            if (i == 0)
                page.setPageSize(jp.getPages().get(i).getElements().size());
            totalCount += jp.getPages().get(i).getElements().size();
        }
        page.setTotalRecord(totalCount);
    }

    /**
     * Writes the download to the output stream
     */
    private void write(HttpServletResponse response, ByteArrayOutputStream baos) {
        try {
            logger.debug(Integer.toString(baos.size()));

            // Retrieve output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to output stream
            baos.writeTo(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            logger.error("Unable to write download to the output stream");
            throw new RuntimeException(e);
        }
    }

}
