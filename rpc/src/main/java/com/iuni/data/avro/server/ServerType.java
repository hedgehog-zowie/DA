package com.iuni.data.avro.server;

/**
 * 服务端类型
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ServerType {
    /**
     * custom type
     */
    OTHER(null),

    /**
     * http server type
     * @see SimpleHttpServer
     */
    HTTP("com.iuni.data.avro.server.SimpleHttpServer"),

    /**
     * netty server type
     * @see SimpleNettyServer
     */
    NETTY("com.iuni.data.avro.server.SimpleNettyServer");

    private final String serverClassName;

    private ServerType(String serverClassName) {
        this.serverClassName = serverClassName;
    }

    public String getServerClassName() {
        return serverClassName;
    }

}
