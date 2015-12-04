package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.BuriedPoint;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedPointService {

    BuriedPoint getById(Long id);

    List<BuriedPoint> listBuriedPoint(final BuriedPoint buriedPoint);

    void addBuriedPoint(BuriedPoint buriedPoint) throws Exception;

    void updateBuriedPoint(BuriedPoint buriedPoint) throws Exception;

    void deleteBuriedPoint(String ids) throws Exception;

    void enableBuriedPoint(String ids) throws Exception;

    void disableBuriedPoint(String ids) throws Exception;

}
