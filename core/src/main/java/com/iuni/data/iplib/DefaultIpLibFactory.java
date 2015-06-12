package com.iuni.data.iplib;

import com.google.common.base.Preconditions;
import com.iuni.data.IpLib;
import com.iuni.data.IpLibFactory;
import com.iuni.data.conf.iplib.IpLibType;
import com.iuni.data.exceptions.IuniDAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * setBasicInfoForCreate ipLib
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class DefaultIpLibFactory implements IpLibFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultIpLibFactory.class);

    /**
     * setBasicInfoForCreate ipLib by type
     * @param name
     * @param type
     * @return
     * @throws IuniDAException
     */
    public IpLib create(String name, String type) throws IuniDAException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of ipLib {}, type {}", name, type);
        Class<? extends IpLib> ipClass = getClass(type);
        try {
            IpLib ipLib = ipClass.newInstance();
            ipLib.setName(name);
            return ipLib;
        } catch (Exception ex) {
            throw new IuniDAException("Unable to setBasicInfoForCreate ipLib: " + name +", type: " + type + ", class: " + ipClass.getName(), ex);
        }
    }

    /**
     * setBasicInfoForCreate ipLib by class reflection
     * @param type
     * @return
     * @throws IuniDAException
     */
    @SuppressWarnings("unchecked")
    public Class<? extends IpLib> getClass(String type) throws IuniDAException {
        String ipLibClassName = type;
        IpLibType ipLibType = IpLibType.OTHER;
        try {
            ipLibType = IpLibType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.debug("ipLib type {} is a custom type", type);
        }
        if (!ipLibType.equals(IpLibType.OTHER)) {
            ipLibClassName = ipLibType.getIpLibClassName();
        }
        try {
            return (Class<? extends IpLib>) Class.forName(ipLibClassName);
        } catch (Exception ex) {
            throw new IuniDAException("Unable to load ipLib type: " + type + ", class: " + ipLibClassName, ex);
        }
    }
}
