package com.iuni.data.iplib;

import com.iuni.data.Context;
import com.iuni.data.IpLib;
import com.iuni.data.conf.Configurable;
import com.iuni.data.lifecycle.LifecycleAware;
import com.iuni.data.lifecycle.LifecycleState;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class AbstractIpLib implements IpLib, LifecycleAware, Configurable {

    private String name;

    private LifecycleState lifecycleState;

    public AbstractIpLib() {
        this.lifecycleState = LifecycleState.IDLE;
    }

    @Override
    public synchronized void start() {
        lifecycleState = LifecycleState.START;
    }

    @Override
    public synchronized void stop() {
        lifecycleState = LifecycleState.STOP;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public void configure(Context context) {

    }

}
