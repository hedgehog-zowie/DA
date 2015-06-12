package com.iuni.data;

import com.google.common.eventbus.Subscribe;
import com.iuni.data.NamedComponent;
import com.iuni.data.iplib.IpInfo;
import com.iuni.data.lifecycle.LifecycleAware;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface IpLib extends LifecycleAware, NamedComponent {

    /**
     * not use
     * @param ipInfo
     */
    @Subscribe
    @Deprecated
    void getIpInfo(IpInfo ipInfo);

    IpInfo getIpInfo(String ipStr);

}
