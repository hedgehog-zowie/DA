package com.iuni.data.avro.client;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ClientType {
    /**
     * custom type
     */
    OTHER(null),

    /**
     * http client type
     *
     * @see SimpleHttpClient
     */
    HTTP("com.iuni.data.avro.client.SimpleHttpClient"),

    /**
     * netty client type
     *
     * @see SimpleNettyClient
     */
    NETTY("com.iuni.data.avro.client.SimpleNettyClient");

    private final String clientClassName;

    private ClientType(String clientClassName){
        this.clientClassName = clientClassName;
    }

    public String getClientClassName() {
        return clientClassName;
    }

}
