package com.iuni.data.conf;

/**
 * 组件类型：
 * 分析组件，服务端，客户端，IP库
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ComponentType {

    ANALYZE("Analyze"),
    IPLIB("IpLib"),
    SERVER("Server"),
    CLIENT("Client"),
    ;

    private final String componentType;

    private ComponentType(String type) {
        componentType = type;
    }

    public String getComponentType() {
        return componentType;
    }
}