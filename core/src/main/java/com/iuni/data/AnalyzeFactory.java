package com.iuni.data;

import com.iuni.data.exceptions.IuniDAException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface AnalyzeFactory {

    /**
     * setBasicInfoForCreate an analyze by existed type
     *
     * @param name
     * @param type
     * @return
     * @throws IuniDAException
     */
    public Analyze create(String name, String type) throws IuniDAException;

    /**
     * get an analyze by reflection
     * @param type
     * @return
     * @throws IuniDAException
     */
    public Class<? extends Analyze> getClass(String type) throws IuniDAException;

}
