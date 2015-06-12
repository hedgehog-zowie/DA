package com.iuni.data.lifecycle;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface LifecycleAware {
    /**
     * start a service or component.
     */
    public void start();

    /**
     * stop a service or component.
     */
    public void stop();

    /**
     * Return the current state of the service or component.
     * @return
     */
    public LifecycleState getLifecycleState();
}
