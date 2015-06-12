package com.iuni.data.iplib;

import com.google.common.collect.ImmutableMap;
import com.iuni.data.IpLib;

/**
 * 实例化后的配置
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface MaterializedConfiguration {

    public void addIpLib(String name, IpLib ipLib);

    public ImmutableMap<String, IpLib> getIpLibs();

}
