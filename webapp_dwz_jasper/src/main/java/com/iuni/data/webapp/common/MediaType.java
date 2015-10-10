package com.iuni.data.webapp.common;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum MediaType {

    html("text/html"),
    csv("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    pptx("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    pdf("application/pdf");

    private final String mediaType;

    MediaType(String mediaType){
        this.mediaType = mediaType;
    }

    public String getMediaType(){
        return this.mediaType;
    }
}
