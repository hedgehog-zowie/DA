package com.iuni.data.app;

import com.google.common.collect.ImmutableMap;
import com.iuni.data.Analyze;

/**
 * 实例化后的配置
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface MaterializedConfiguration {

    public void addAnalyze(String name, Analyze analyze);

    public ImmutableMap<String, Analyze> getAnalyzes();

}
