package com.iuni.data.iplib;

import com.google.common.collect.ImmutableMap;
import com.iuni.data.IpLib;

import java.util.HashMap;
import java.util.Map;

/**
 * 实例化配置的简单实现
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleMaterializedConfiguration implements MaterializedConfiguration {

    private final Map<String, IpLib> ipLibMap;

    public SimpleMaterializedConfiguration(){
        ipLibMap = new HashMap<String, IpLib>();
    }

    @Override
    public void addIpLib(String name, IpLib ipLib) {
        ipLibMap.put(name, ipLib);
    }

    @Override
    public ImmutableMap<String, IpLib> getIpLibs() {
        return ImmutableMap.copyOf(ipLibMap);
    }

}
