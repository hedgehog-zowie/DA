package com.iuni.data.conf.client;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ClientType {
    OTHER(null),
    HTTP("com.iuni.data.avro.client.SimpleHttpClient"),
    NETTY("com.iuni.data.avro.client.SimpleNettyClient");

    private String clientClassName;

    private ClientType(String clientClassName) {
        this.clientClassName = clientClassName;
    }

    public String getClientClassName() {
        return this.clientClassName;
    }
}
