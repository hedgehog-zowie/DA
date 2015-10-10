package com.iuni.data.webapp.service;

import com.iuni.data.webapp.common.ExportType;
import com.iuni.data.webapp.common.MediaType;
import com.iuni.data.webapp.common.PageVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ExporterService {

    public HttpServletResponse export(String name, String type,
                                      JasperPrint jp, HttpServletResponse response,
                                      ByteArrayOutputStream baos) throws UnsupportedEncodingException {

        if (ExportType.csv.getType().equalsIgnoreCase(type)) {
            // Export to output stream
            exportCsv(jp, baos);

            // Set our response properties
            // Here you can declare a custom filename
            String fileName = name + ExportType.csv.getSuffix();
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            // Set content type
            response.setContentType(MediaType.csv.getMediaType());
            response.setContentLength(baos.size());

            return response;
        } else if (ExportType.xlsx.getType().equalsIgnoreCase(type)) {
            // Export to output stream
            exportXlsx(jp, baos);

            // Set our response properties
            // Here you can declare a custom filename
            String fileName = name + ExportType.xlsx.getSuffix();
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            // Set content type
            response.setContentType(MediaType.xlsx.getMediaType());
            response.setContentLength(baos.size());

            return response;
        } else if (ExportType.pptx.getType().equalsIgnoreCase(type)) {
            // Export to output stream
            exportPPTX(jp, baos);

            // Set our response properties
            // Here you can declare a custom filename
            String fileName = name + ExportType.pptx.getSuffix();
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            // Set content type
            response.setContentType(MediaType.pptx.getMediaType());
            response.setContentLength(baos.size());

            return response;
        } else if (ExportType.pdf.getType().equalsIgnoreCase(type)) {
            // Export to output stream
            exportPdf(jp, baos);

            // Set our response properties
            // Here you can declare a custom filename
            String fileName = name + ExportType.pdf.getSuffix();
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);

            // Set content type
            response.setContentType(MediaType.pdf.getMediaType());
            response.setContentLength(baos.size());

            return response;
        }

        throw new RuntimeException("No type set for type " + type);
    }

    public void exportHtml(JasperPrint jp, ByteArrayOutputStream baos){
        exportHtml(null, jp, baos);
    }

    public void exportHtml(PageVO page, JasperPrint jp, ByteArrayOutputStream baos) {
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(baos, "GBK"));
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        HtmlSizeUnitEnum htmlSizeUnitEnum = HtmlSizeUnitEnum.POINT;
        configuration.setSizeUnit(htmlSizeUnitEnum);
        if(null != page) {
            configuration.setPageIndex(page.getCurrentPage() - 1);
        }
        exporter.setConfiguration(configuration);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportCsv(JasperPrint jp, ByteArrayOutputStream baos) {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(baos));
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportXlsx(JasperPrint jp, ByteArrayOutputStream baos) {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        exporter.setConfiguration(configuration);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportPPTX(JasperPrint jp, ByteArrayOutputStream baos) {
        JRPptxExporter exporter = new JRPptxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportPdf(JasperPrint jp, ByteArrayOutputStream baos) {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jp));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        try {
            exporter.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

}
