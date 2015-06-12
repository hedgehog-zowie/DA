package com.iuni.data;

import com.iuni.data.exceptions.IuniDAException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface IpLibFactory {

    /**
     * setBasicInfoForCreate an ipLib by existed type
     *
     * @param name
     * @param type
     * @return
     * @throws IuniDAException
     */
    public IpLib create(String name, String type) throws IuniDAException;

    /**
     * setBasicInfoForCreate an ipLib by reflection
     *
     * @param type
     * @return
     * @throws IuniDAException
     */
    public Class<? extends IpLib> getClass(String type) throws IuniDAException;

}
