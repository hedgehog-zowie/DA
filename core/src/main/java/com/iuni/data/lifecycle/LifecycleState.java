package com.iuni.data.lifecycle;

/**
 * 生命周期各状态
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum LifecycleState {
    IDLE, START, STOP, ERROR;

    public static final LifecycleState[] START_OR_ERROR = new LifecycleState[]{
            START, ERROR};
    public static final LifecycleState[] STOP_OR_ERROR = new LifecycleState[]{
            STOP, ERROR};

}
