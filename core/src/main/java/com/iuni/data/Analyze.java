package com.iuni.data;

import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.TType;
import com.iuni.data.lifecycle.LifecycleAware;

import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface Analyze extends LifecycleAware, NamedComponent {

    /**
     * analyze by time string
     * @param startTime
     * @param endTime
     * @param tType
     */
    void analyze(String startTime, String endTime, TType tType, boolean createPartition);

    /**
     * analyze
     * @param startTime
     * @param endTime
     * @param tType
     */
    void analyze(Date startTime, Date endTime, TType tType, boolean createPartition);

}
