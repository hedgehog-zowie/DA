package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ExportType {

    html("html", ".html"),
    csv("csv", ".csv"),
    xlsx("xlsx", ".xlsx"),
    pptx("pptx", ".pptx"),
    pdf("pdf", ".pdf");

    private final String type;
    private final String suffix;

    ExportType(String type, String suffix){
        this.type = type;
        this.suffix = suffix;
    }

    public String getType(){
        return this.type;
    }

    public String getSuffix(){
        return this.suffix;
    }
}
