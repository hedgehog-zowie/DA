package com.iuni.data.app;

import com.google.common.collect.ImmutableMap;
import com.iuni.data.Analyze;
import com.iuni.data.IpLib;

import java.util.HashMap;
import java.util.Map;

/**
 * implement MaterializedConfiguration
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class SimpleMaterializedConfiguration implements MaterializedConfiguration {

    private final Map<String, Analyze> analyzeMap;

    public SimpleMaterializedConfiguration(){
        analyzeMap = new HashMap<String, Analyze>();
    }

    @Override
    public void addAnalyze(String name, Analyze analyze) {
        analyzeMap.put(name, analyze);
    }

    @Override
    public ImmutableMap<String, Analyze> getAnalyzes() {
        return ImmutableMap.copyOf(analyzeMap);
    }

}
