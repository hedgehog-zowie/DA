package com.iuni.data.conf.server;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ServerType {
    OTHER(null),
    HTTP("com.iuni.data.avro.server.SimpleHttpServer"),
    NETTY("com.iuni.data.avro.server.SimpleNettyServer");

    private String serverClassName;

    private ServerType(String serverClassName) {
        this.serverClassName = serverClassName;
    }

    public String getServerClassName() {
        return this.serverClassName;
    }
}
